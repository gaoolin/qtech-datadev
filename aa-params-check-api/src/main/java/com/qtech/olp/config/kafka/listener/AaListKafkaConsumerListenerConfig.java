package com.qtech.olp.config.kafka.listener;

import com.qtech.olp.kafka.BatchMessageListener;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 10:50:10
 * desc   :
 */

//@Configuration
public class AaListKafkaConsumerListenerConfig {
    /**
     * 创建 消费者对象
     *
     * @param consumerFactory
     * @return
     */
    @Bean
    public Consumer<?, ?> consumer(ConsumerFactory<Object, Object> consumerFactory) {
        return consumerFactory.createConsumer();
    }

    /**
     * 消息监听器容器
     */
    @Bean
    public ConcurrentMessageListenerContainer<?, ?> messageListenerContainer(ConsumerFactory<Object, Object> consumerFactory) {
        ContainerProperties containerProperties = new ContainerProperties("qtech_im_aa_list_topic");
        containerProperties.setMessageListener(new BatchMessageListener());
        return new ConcurrentMessageListenerContainer<>(consumerFactory, containerProperties);
    }

    @Bean
    public ConsumerFactory<?, ?> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(buildConsumerProperties());
    }


    /**
     * 构建消费者配置
     *
     * @return
     */
    public static Map<String, Object> buildConsumerProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("bootstrap.servers", "10.170.6.25:9092");
        properties.put("group.id", "aaList");
        properties.put("enable.auto.commit", false);
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        return properties;
    }
}
