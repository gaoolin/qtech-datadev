package com.qtech.check.config.kafka.common;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 09:47:54
 * desc   :
 */

@Configuration
public class AaListParamsKafkaConsumerConfig {

    /**
     * 创建 消费者对象
     *
     * @param
     * @return
     */
    @Bean
    @Qualifier("aaListParamsCommonKafkaConsumer")
    public Consumer<?, ?> consumer() {
        return new DefaultKafkaConsumerFactory<>(buildConsumerProperties()).createConsumer();
    }

    /**
     * 构建消费者配置
     *
     * @return
     */
    public static Map<String, Object> buildConsumerProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("bootstrap.servers", "10.170.6.25:9092,10.170.6.26:9092");
        properties.put("group.id", "aaList-message-group");
        properties.put("enable.auto.commit", false);
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return properties;
    }
}
