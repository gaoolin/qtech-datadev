package com.qtech.check.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.BatchAcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 10:54:09
 * desc   :  Kafka批量消息监听器的实现
 */

/**
 * @param
 * @description 如果需要访问Consumer实例来进行更细粒度的操作，比如手动提交偏移量或获取元数据，应该实现BatchConsumerAwareMessageListener接口。
 * 这个接口提供了额外的Consumer参数，允许你直接与Kafka Consumer交互。
 * <p>
 * 原本想要处理的是ConsumerRecords而非List<ConsumerRecord>，这通常与批量处理更加相关。如果是这种情况，请确保你的配置和监听器设计与期望的接口相匹配。
 * 如果目标是处理批量消息且需要访问Consumer实例，确保你正确实现了适用于批量处理的接口，并且方法签名与之相符。
 * @return
 */
// @Component
public class BatchMessageListener implements BatchAcknowledgingMessageListener<String, String> {

    @Override
    public void onMessage(List<ConsumerRecord<String, String>> records, Acknowledgment acknowledgment) {
        for (ConsumerRecord<String, String> record : records) {
            // 处理每条消息
            processMessage(record.key(), record.value());
        }
        // 批量确认
        acknowledgment.acknowledge();
    }

    private void processMessage(String key, String value) {
        // 实现你的消息处理逻辑
    }
}
