package com.qtech.mq.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.mq.domain.EqReverseCtrlInfo;
import com.qtech.mq.serializer.Record;
import com.qtech.mq.serializer.RecordSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 19:56:11
 * desc   :
 */
// @Component
// @EnableKafkaStreams
public class WbOlpChkKafkaDeduplicationTopology {
    private static final Logger logger = LoggerFactory.getLogger(WbOlpChkKafkaDeduplicationTopology.class);

    // @Autowired
    private RabbitTemplate rabbitTemplate;

    // @Autowired
    private StreamsBuilder streamsBuilder;

    // @Autowired
    private ObjectMapper objectMapper;

    // @PostConstruct
    public void init() {
        logger.info(">>>>> Initializing Kafka Streams topology...");
        createTopology(streamsBuilder);
    }

    public void createTopology(StreamsBuilder streamsBuilder) {
        logger.info(">>>>> Creating topology for topic qtech_im_wb_olp_chk_topic");
        Serde<Record> valueSerde = new RecordSerde();
        KStream<Record, Record> inputStream = streamsBuilder.stream("qtech_im_wb_olp_chk_topic", Consumed.with(valueSerde, valueSerde));

        inputStream.foreach((key, value) -> logger.info(">>>>> Received record: key = {}, value = {}", key, value));

        inputStream.groupByKey().reduce((oldValue, newValue) -> {
                    if (oldValue != null) {
                        // 如果旧值存在，返回旧值，丢弃新值
                        logger.info(">>>>> Duplicate record detected, ignoring new record: oldValue = {}, newValue = {}", oldValue, newValue);
                        return oldValue;
                    } else {
                        // 如果旧值不存在，保留新值
                        // sendToRabbitMQ(newValue);
                        return newValue;
                    }
                }, Materialized.<Record, Record, KeyValueStore<Bytes, byte[]>>as("wbOlpChkStreamStateStore")
                        .withKeySerde(valueSerde)
                        .withValueSerde(valueSerde)
                        .withRetention(Duration.ofMinutes(30)))
                .toStream();
    }


    public void sendToRabbitMQ(Record record) {
        if (record == null) {
            logger.warn(">>>>> Record is null, skipping sending to RabbitMQ");
            return;
        }
        try {
            EqReverseCtrlInfo eqReverseCtrlInfo = new EqReverseCtrlInfo();

            eqReverseCtrlInfo.setSimId(String.valueOf(record.getSimId()));
            eqReverseCtrlInfo.setProdType(String.valueOf(record.getProdType()));
            eqReverseCtrlInfo.setChkDt(String.valueOf(record.getChkDt()));
            eqReverseCtrlInfo.setCode(Integer.parseInt(String.valueOf(record.getCode())));
            eqReverseCtrlInfo.setDescription(String.valueOf(record.getDescription()));

            // 手动处理 code 属性的类型转换

            eqReverseCtrlInfo.setSource("wb-olp");

            // 使用配置化的交换机和队列名称
            String exchangeName = "qtechImExchange";
            String routingKey = "eqReverseCtrlInfoQueue"; // 此处应该是路由键，而不是队列名称
            rabbitTemplate.convertAndSend(exchangeName, routingKey, objectMapper.writeValueAsString(eqReverseCtrlInfo));

            // 记录关键字段，避免敏感信息泄漏
            logger.info(">>>>> Record sent to RabbitMQ: id={}, source={}", eqReverseCtrlInfo.getId(), eqReverseCtrlInfo.getSource());
        } catch (JsonProcessingException | NumberFormatException e) {
            logger.error("Failed to convert Record to JSON or copy properties", e);
        }
    }
}