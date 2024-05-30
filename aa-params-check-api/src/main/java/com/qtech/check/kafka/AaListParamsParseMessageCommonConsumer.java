package com.qtech.check.kafka;

import com.alibaba.fastjson.JSON;
import com.qtech.check.pojo.AaListParams;
import com.qtech.check.processor.MessageProcessor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 10:06:00
 * desc   :  它订阅了一个名为"qtech_im_aa_list_topic"的主题并持续监听新消息。@PostConstruct注解表示当该bean初始化时，会执行AaListKafkaConsumer方法
 * <p>
 * 1. 异常处理与重试逻辑
 * 增加异常捕获与日志记录：确保在消费循环中捕获所有异常，并记录详细日志。
 * 实现重试逻辑：对于可恢复的错误，可以设计简单的重试机制，例如在捕获到特定异常后等待一段时间后重新尝试消费。
 * 2. 线程管理
 * 使用ScheduledExecutorService：考虑使用ScheduledExecutorService替代基本的ExecutorService，以便于实现更灵活的线程控制，比如定时任务的执行、延迟执行或定期执行。
 * 优雅停机：确保在应用关闭时能优雅地关闭消费者和线程池，避免资源泄露
 * <p>
 * 注意事项
 * 使用consumer.wakeup()来安全地中断consumer.poll()，这是Kafka推荐的方式来停止消费者线程。
 * ScheduledExecutorService用于周期性地执行消费逻辑，你可以根据需要调整执行间隔。
 * 在stopConsumer方法中，先发送wakeup信号，然后优雅地关闭线程池和消费者，确保资源得到正确释放。
 * 通过这种方式，你可以保持直接使用Consumer的灵活性，同时实现类似于KafkaMessageListenerContainer的管理功能。
 */

@Component
public class AaListParamsParseMessageCommonConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AaListParamsParseMessageCommonConsumer.class);
    private volatile boolean isRunning = true;

    @Autowired
    @Qualifier("aaListParamsCommonKafkaConsumer")
    private Consumer<String, Object> consumer;

    @Autowired
    private MessageProcessor messageProcessor;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // 使用ScheduledExecutorService管理线程，便于控制和关闭
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    @PostConstruct
    public void startConsumer() {
        consumer.subscribe(Arrays.asList("qtech_im_aa_list_topic"));
        executorService.scheduleWithFixedDelay(this::pollAndProcessRecords, 0, 1, TimeUnit.SECONDS);
    }

    private void pollAndProcessRecords() {
        if (!isRunning) return;
        try {
            ConsumerRecords<String, Object> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, Object> record : records) {
                handleRecord(record);
            }
        } catch (WakeupException e) {
            if (isRunning) {
                logger.error("Unexpected WakeupException during consumption", e);
            }
        } catch (Exception e) {
            logger.error("Error consuming message", e);
        }
    }

    private void handleRecord(ConsumerRecord<String, Object> record) {
        String aaListMessageStr = (String) record.value();
        if (aaListMessageStr != null) {
            try {
                // 获取当前时间戳
                long currentTimeMillis = System.currentTimeMillis();

                // 处理消息并转换为 AaListParams
                AaListParams aaListParams = messageProcessor.processMessage(AaListParams.class, aaListMessageStr);

                logger.info("Received message: {}", aaListMessageStr);
                // 将 AaListParams 对象转换为 JSON 字符串
                String aaListParamsMessageStr = JSON.toJSONString(aaListParams);

                // 创建消息头集合并添加时间戳消息头
                Header timestampHeader = new RecordHeader("received-timestamp", Long.toString(currentTimeMillis).getBytes());

                // 将消息头添加到消息中
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>("qtech_im_aa_list_parsed_topic", null, null, aaListParamsMessageStr);
                producerRecord.headers().add(timestampHeader);

                // 将解析后的消息发送到另一个 Kafka 主题
                kafkaTemplate.send(producerRecord);

                logger.info("Parsed message successfully: {}", aaListParamsMessageStr);
            } catch (Exception e) {
                // 记录处理消息时出现的异常
                logger.error("Error processing message: {}", aaListMessageStr, e);
            }
        } else {
            // 记录接收到空消息的情况
            logger.warn("Received null or empty message from topic: {}", record.topic());
        }
    }

    public void stopConsumer() {
        synchronized (this) {
            isRunning = false;
            // 发送Wakeup signal来中断poll()
            consumer.wakeup();
            try {
                executorService.shutdown();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.warn("Executor did not terminate in the specified time.");
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                logger.error("Interrupted while waiting for executor to terminate", e);
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            } finally {
                try {
                    consumer.close();
                } catch (Exception e) {
                    logger.error("Error closing consumer", e);
                }
            }
        }
    }
}
