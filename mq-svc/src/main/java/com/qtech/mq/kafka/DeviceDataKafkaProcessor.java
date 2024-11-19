package com.qtech.mq.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.mq.domain.DeviceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/11/19 15:52:37
 * desc   :
 */

@Service
public class DeviceDataKafkaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DeviceDataKafkaProcessor.class);

    // 定义有效的设备类型集合
    private static final Set<String> VALID_DEVICE_TYPES = new HashSet<>(Arrays.asList("WB", "HM", "DB", "AA"));

    // 定义正则表达式
    private static final String VALID_DEVICE_TYPE_PATTERN = ".*(" + String.join("|", VALID_DEVICE_TYPES) + ").*";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${kafka.target.topic}")
    private String targetTopic;

    @Autowired
    private KafkaTemplate<String, String> targetKafkaTemplate;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @KafkaListener(
            topics = "${kafka.source.topic}",
            containerFactory = "sourceKafkaListenerContainerFactory"
    )
    public void consumeAndForward(String message) {
        try {
            logger.debug(">>>>> Received message: " + message);
            DeviceData deviceData = objectMapper.readValue(message, DeviceData.class);
            String deviceType = deviceData.getDeviceType();
            String deviceId = deviceData.getDeviceId();

            // 将 deviceType 转换为大写
            deviceType = deviceType.toUpperCase();

            // 使用正则表达式进行模糊匹配
            if (!deviceType.matches(VALID_DEVICE_TYPE_PATTERN)) {
                logger.info(">>>>> Invalid device type: " + deviceType);
                return;
            }

            // 存入 Redis
            String redisKey = "device_status:" + deviceId;
            Boolean isExist = stringRedisTemplate.hasKey(redisKey);
            if (Boolean.FALSE.equals(isExist)) {
                stringRedisTemplate.opsForValue().set(redisKey, message, 180, TimeUnit.SECONDS);

                // 将 DeviceData 对象转换为 JSON 字符串
                String deviceDataJson = objectMapper.writeValueAsString(deviceData);
                targetKafkaTemplate.send(targetTopic, deviceId, deviceDataJson);
                logger.info(">>>>> Forwarded message to target cluster: " + deviceDataJson);
            }
        } catch (JsonProcessingException e) {
            logger.error(">>>>> Error parsing JSON message: " + message, e);
        } catch (Exception e) {
            logger.error(">>>>> Unexpected error processing message: " + message, e);
        }
    }
}
