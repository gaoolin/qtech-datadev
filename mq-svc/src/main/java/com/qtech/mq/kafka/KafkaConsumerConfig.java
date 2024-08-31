package com.qtech.mq.kafka;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/30 08:50:43
 * desc   :
 */

import com.qtech.mq.serializer.Record;
import com.qtech.mq.serializer.RecordValueDeserializer;
import com.qtech.mq.serializer.WbOlpRawDataRecord;
import com.qtech.mq.serializer.WbOlpRawDataRecordDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String, String> consumerStringFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerStringFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerStringFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Record> consumerRecordFactory() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, RecordValueDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Record> kafkaListenerContainerRecordFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Record> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerRecordFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, WbOlpRawDataRecord> consumerWbOlpRawDataRecordFactory() {
        HashMap<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, WbOlpRawDataRecordDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WbOlpRawDataRecord> kafkaListenerContainerWbOlpRawDataRecordFactory() {
        ConcurrentKafkaListenerContainerFactory<String, WbOlpRawDataRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerWbOlpRawDataRecordFactory());
        return factory;
    }
}