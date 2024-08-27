package com.qtech.mq.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.mq.domain.EqReverseCtrlInfo;
import com.qtech.mq.serializer.CompositeKey;
import com.qtech.mq.serializer.CompositeKeySerde;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 19:56:11
 * desc   :
 */
@Component
public class WbOlpChkKafkaDeduplicationTopology {
    private static final Logger logger = LoggerFactory.getLogger(WbOlpChkKafkaDeduplicationTopology.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StreamsBuilder streamsBuilder;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.kafka.store.name}")
    private String storeName;  // 注入 store.name 配置

    @PostConstruct
    public void init() {
        logger.info(">>>>> Initializing Kafka Streams topology...");
        createTopology(streamsBuilder);
    }

    public void createTopology(StreamsBuilder streamsBuilder) {
        logger.info(">>>>> Creating topology for topic qtech_im_wb_olp_chk_topic");
        Serde<CompositeKey> keySerde = new CompositeKeySerde();
        Serde<Record> valueSerde = new RecordSerde();
        KStream<CompositeKey, Record> inputStream = streamsBuilder.stream("qtech_im_wb_olp_chk_topic", Consumed.with(keySerde, valueSerde));

        inputStream.foreach((key, value) -> logger.info(">>>>> Received record: key = {}, value = {}", key, value));

        inputStream
                .groupByKey()
                .reduce((oldValue, newValue) -> oldValue != null ? oldValue : newValue,
                        Materialized.<CompositeKey, Record, KeyValueStore<Bytes, byte[]>>as(storeName)
                                .withKeySerde(new CompositeKeySerde())
                                .withValueSerde(new RecordSerde())
                                .withRetention(Duration.ofMinutes(15)))
                .toStream()
                .foreach((key, value) -> {
                    logger.info(">>>>> Deduplicated record: key = {}, value = {}", key, value);
                    sendToRabbitMQ(value);
                });
    }


    public void sendToRabbitMQ(Record record) {
        try {
            EqReverseCtrlInfo eqReverseCtrlInfo = new EqReverseCtrlInfo();

            // 使用 Spring 的 BeanUtils.copyProperties 复制除了 code 之外的所有属性
            BeanUtils.copyProperties(record, eqReverseCtrlInfo, "code");

            // 手动处理 code 属性的类型转换
            Integer code = StringUtils.hasLength(record.getCode()) ? Integer.parseInt(record.getCode()) : null;
            eqReverseCtrlInfo.setCode(code);

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