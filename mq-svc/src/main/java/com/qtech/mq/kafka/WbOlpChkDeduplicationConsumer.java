package com.qtech.mq.kafka;

import com.qtech.mq.domain.EqReverseCtrlInfo;
import com.qtech.mq.serializer.Record;
import com.qtech.mq.service.IEqReverseCtrlInfoService;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.qtech.mq.common.Constants.KAFKA_TOPIC;
import static com.qtech.mq.common.Constants.REDIS_OLP_CHECK_DUPLICATION_KEY_PREFIX;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/29 09:58:54
 * desc   :
 */

@EnableKafka
@Service
public class WbOlpChkDeduplicationConsumer {
    private static final Logger logger = LoggerFactory.getLogger(WbOlpChkDeduplicationConsumer.class);
    private static final int BATCH_SIZE = 100; // 批处理的大小
    // 用于存储批量处理的 List
    private final List<EqReverseCtrlInfo> messageList = new ArrayList<>();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private IEqReverseCtrlInfoService eqReverseCtrlInfoService;

    @KafkaListener(topics = KAFKA_TOPIC,
            groupId = "wb-olp-chk-consumer-group",
            containerFactory = "kafkaListenerContainerRecordFactory")
    public void consume(ConsumerRecord<Long, Record> record) throws Exception {
        String redisKey = generateRedisKey(record.value());

        // 检查 Redis 中是否存在该 key
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(REDIS_OLP_CHECK_DUPLICATION_KEY_PREFIX + redisKey))) {
            // 如果 Redis 中没有该 key，先保存到 Redis
            String jsonRecord = convertRecordToJson(record.value());
            redisTemplate.opsForValue().set(REDIS_OLP_CHECK_DUPLICATION_KEY_PREFIX + redisKey, jsonRecord, 30, TimeUnit.MINUTES);
            // redisTemplate.opsForValue().set(REDIS_OLP_CHECK_DUPLICATION_KEY_PREFIX + redisKey, objectMapper.writeValueAsString(record.value()), 30, TimeUnit.MINUTES);

            // 然后将数据转换为 EqReverseCtrlInfo 并添加到 List
            EqReverseCtrlInfo eqInfo = convertToEqReverseCtrlInfo(record.value());

            // 过渡
            // redisTemplate.opsForValue().set(REDIS_OLP_CHECK_WB_OLP_KEY_PREFIX + eqInfo.getSimId(), eqInfo.getCode() + "*" + eqInfo.getDescription(), 30, TimeUnit.MINUTES);
            // eqReverseCtrlInfoService.upsertPostgresAsync(eqInfo);
            eqReverseCtrlInfoService.upsertOracleAsync(eqInfo);
            eqReverseCtrlInfoService.upsertDorisAsync(eqInfo);
            eqReverseCtrlInfoService.addWbOlpChkDorisAsync(eqInfo);
            logger.info(">>>>> WbOlpChk deduplication record consumed: " + record.value());
        }
    }

    private String generateRedisKey(Record record) {
        if (record == null) {
            return null;
        }
        return record.getSimId() +
                "|" + record.getProdType() +
                "|" + String.valueOf(record.getChkDt()).replace(" ", "").replace(":", "") +
                "|" + record.getCode() +
                "|" + record.getDescription();
    }

    private EqReverseCtrlInfo convertToEqReverseCtrlInfo(Record record) {
        EqReverseCtrlInfo eqInfo = new EqReverseCtrlInfo();
        eqInfo.setSimId(String.valueOf(record.getSimId()));
        eqInfo.setSource("wb-olp");
        eqInfo.setProdType(String.valueOf(record.getProdType()));
        eqInfo.setChkDt(String.valueOf(record.getChkDt()));
        eqInfo.setCode(Integer.valueOf(String.valueOf(record.getCode())));
        eqInfo.setDescription(String.valueOf(record.getDescription()));
        return eqInfo;
    }

    private String convertRecordToJson(Record record) throws IOException {
        DatumWriter<Record> writer = new SpecificDatumWriter<>(Record.getClassSchema());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().jsonEncoder(Record.getClassSchema(), outputStream);
        writer.write(record, encoder);
        encoder.flush();
        return outputStream.toString("UTF-8");
    }
}