package com.qtech.check.config.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 10:50:10
 * desc   :
 */

@Slf4j
@Configuration
@EnableKafka
public class kafkaListenerContainerFactoryConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true); // 设置为批处理模式
        return factory;
    }

    private ConsumerFactory<String, String> consumerFactory() {
        // 配置ConsumerFactory，这里可以根据需要添加其他配置
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.170.6.25:9092");
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1024);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        // 其他配置...
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * @param
     * @return org.springframework.kafka.listener.ConcurrentMessageListenerContainer<?, ?>
     * @description *
     * * 消息监听器容器
     * * 利用Spring Kafka的特性，如自动提交偏移量、错误处理和线程池管理。
     * * 有两种方法使用监听容器
     * * 1. 使用KafkaListener注解，在方法上添加@KafkaListener注解，并指定topic和分区。
     * * 2. 使用注入KafkaMessageListenerContainer，通过编程方式创建监听容器。
     * * KafkaMessageListenerContainer是Spring Kafka提供的另一个组件，它是ConcurrentMessageListenerContainer的基础类。KafkaMessageListenerContainer处理与Kafka消费者的低级别交互，
     * * 包括连接、订阅主题和接收消息。而ConcurrentMessageListenerContainer是KafkaMessageListenerContainer的扩展，增加了并发处理消息的能力，即它能够同时处理多个消息，提高了处理效率。
     */

    /**
     * @param
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @description 构建消费者配置
     */
    public static Map<String, Object> buildConsumerProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("bootstrap.servers", "10.170.6.25:9092");
        properties.put("group.id", "aaList");
        properties.put("enable.auto.commit", false);
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("session.timeout.ms", "30000");
        // properties.put("key.deserializer", StringDeserializer.class.getName());
        // properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return properties;
    }
}
