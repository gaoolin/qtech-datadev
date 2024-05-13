package com.qtech.olp.kafka;

import com.qtech.olp.entity.AaListMessage;
import com.qtech.olp.processor.MessageProcessor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 10:06:00
 * desc   :  它订阅了一个名为"qtech_im_aa_list_topic"的主题并持续监听新消息。@PostConstruct注解表示当该bean初始化时，会执行AaListKafkaConsumer方法
 */

@Component
public class AaListKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AaListKafkaConsumer.class);
    private volatile boolean isRunning = true;

    @Autowired
    private Consumer<String, Object> consumer;

    @Autowired
    private MessageProcessor messageProcessor;

    // 使用 ExecutorService 管理线程
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void listConsumer() {
        // 防止多次启动消费者线程
        synchronized (this) {
            consumer.subscribe(Arrays.asList("qtech_im_aa_list_topic"));
            executorService.submit(() -> {
                while (isRunning) {
                    try {
                        ConsumerRecords<String, Object> records = consumer.poll(100);
                        for (ConsumerRecord<String, Object> record : records) {
                            String aaListMessageStr = (String) record.value();
                            if (aaListMessageStr != null) {
                                // 处理消息
                                AaListMessage aaListMessage = messageProcessor.processMessage(AaListMessage.class, aaListMessageStr);
                                System.out.println("11111" + aaListMessage);
                            }
//                            logger.info("消费消息： " + record.value());
                        }
                    } catch (Exception e) {
                        logger.error("消费消息时发生错误", e);
                    }
                }
            });
        }
    }

    // 添加一个方法来优雅地停止消费者线程
    public void stopConsumer() {
        synchronized (this) {
            isRunning = false;
            try {
                // 关闭消费者并等待已提交任务完成
                consumer.close();
                executorService.shutdown();
                executorService.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                logger.error("线程中断异常", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    public void startConsumer() {
        synchronized (this) {
            isRunning = true;
        }
    }
}
