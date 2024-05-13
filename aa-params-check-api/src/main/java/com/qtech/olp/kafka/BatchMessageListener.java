package com.qtech.olp.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.BatchConsumerAwareMessageListener;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 10:54:09
 * desc   :
 */


public class BatchMessageListener implements BatchConsumerAwareMessageListener<Object, Object> {

    @Override
    public void onMessage(List<ConsumerRecord<Object, Object>> data, Consumer<?, ?> consumer) {

        for (ConsumerRecord<Object, Object> record : data) {
            System.out.println("消费者：" + record.value());
            consumer.commitAsync();
        }
    }
}
