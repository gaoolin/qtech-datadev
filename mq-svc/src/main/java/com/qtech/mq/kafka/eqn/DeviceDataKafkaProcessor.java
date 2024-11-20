package com.qtech.mq.kafka.eqn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

/**
 * date   :  2024/11/19 15:52:37
 * desc   :
 */

@Service
public class DeviceDataKafkaProcessor {
    private static final String REDIS_KEY_PREFIX = "qtech:im:device_status:";
    private static final int REDIS_EXPIRE_SECONDS = 180;

    private static final Logger logger = LoggerFactory.getLogger(DeviceDataKafkaProcessor.class);

    private static final Set<String> VALID_DEVICE_TYPES = new HashSet<>(Arrays.asList("DB", "WB", "HM", "AA"));
    private static final Set<String> VALID_DEVICE_TYPES_A = new HashSet<>(Arrays.asList("DB", "HM", "AA"));

    private static final Set<String> SPECIAL_DEVICE_TYPES = new HashSet<>(Arrays.asList("LINUXRSAA"));

    private static final ObjectMapper objectMapper = configureObjectMapper();

    @Value("${kafka.target.topic}")
    private String targetTopic;

    @Autowired
    private KafkaTemplate<String, String> targetKafkaTemplate;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    private static ObjectMapper configureObjectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .enable(MapperFeature.AUTO_DETECT_GETTERS)
                .enable(MapperFeature.AUTO_DETECT_IS_GETTERS)
                .enable(MapperFeature.AUTO_DETECT_SETTERS)
                .enable(MapperFeature.AUTO_DETECT_FIELDS)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @KafkaListener(
            topics = "${kafka.source.topic}",
            containerFactory = "sourceKafkaListenerContainerFactory"
    )
    public void consumeAndForward(String message) {
        try {
            logger.debug(">>>>> Received message with ID: {}", message);
            DeviceData deviceData = parseMessage(message);
            String deviceType = deviceData.getDeviceType().toUpperCase();
            String deviceId = deviceData.getDeviceId();

            if (!VALID_DEVICE_TYPES.contains(deviceType)) {
                logger.info(">>>>> Invalid device type: " + deviceType);
                return;
            }

            String remoteControlEq = deviceData.getRemoteControl();

            updateDeviceStatus(deviceData, deviceType, remoteControlEq);

            handleRedisAndKafka(deviceData, deviceId);
        } catch (JsonProcessingException e) {
            logger.error(">>>>> Error parsing JSON message: " + message, e);
        } catch (NullPointerException e) {
            logger.error(">>>>> Null pointer exception while processing message: " + message, e);
        } catch (IllegalArgumentException e) {
            logger.error(">>>>> Illegal argument exception while processing message: " + message, e);
        } catch (Exception e) {
            logger.error(">>>>> Unexpected error processing message: " + message, e);
        }
    }

    private DeviceData parseMessage(String message) throws JsonProcessingException {
        return objectMapper.readValue(message, DeviceData.class);
    }

    private void updateDeviceStatus(DeviceData deviceData, String deviceType, String remoteControlEq) {
        if (SPECIAL_DEVICE_TYPES.contains(deviceType) && remoteControlEq == null) {
            logger.info(">>>>> Device type " + deviceType + " does not support remote control.");
            deviceData.setStatus("ONLINE");
            deviceData.setRemoteControl("2");
        } else if (remoteControlEq == null) {
            deviceData.setStatus("ONLINE");
            deviceData.setRemoteControl("999");
        } else {
            if ((VALID_DEVICE_TYPES_A.contains(deviceType) && "2".equals(remoteControlEq)) || ("WB".equals(deviceType) && "5".equals(remoteControlEq))) {
                deviceData.setStatus("ONLINE");
            } else {
                deviceData.setStatus("OFFLINE");
            }
        }
    }


    private void handleRedisAndKafka(DeviceData deviceData, String deviceId) {
        String redisKey = REDIS_KEY_PREFIX + deviceId;
        String redisVal = stringRedisTemplate.opsForValue().get(redisKey);
        if (redisVal == null) {
            forwardToDeviceCluster(deviceData, deviceId, redisKey);
        } else {
            handleExistingDevice(deviceData, deviceId, redisKey);
        }
    }

    private void forwardToDeviceCluster(DeviceData deviceData, String deviceId, String redisKey) {
        try {
            String deviceDataJson = objectMapper.writeValueAsString(deviceData);
            stringRedisTemplate.opsForValue().set(redisKey, deviceDataJson, REDIS_EXPIRE_SECONDS, TimeUnit.SECONDS);
            targetKafkaTemplate.send(targetTopic, deviceId, deviceDataJson);
            logger.info(">>>>> Forwarded message to target cluster: " + deviceDataJson);
        } catch (JsonProcessingException e) {
            logger.error(">>>>> Error converting DeviceData to JSON: " + deviceData, e);
        }
    }

    private void handleExistingDevice(DeviceData deviceData, String deviceId, String redisKey) {
        String redisVal = stringRedisTemplate.opsForValue().get(redisKey);
        if (redisVal == null) {
            logger.warn(">>>>> Redis value is null for key: " + redisKey);
            return;
        }
        try {
            String remoteControlRedis = objectMapper.readTree(redisVal).get("Remote_control").asText();
            if (!remoteControlRedis.equals(deviceData.getRemoteControl())) {
                forwardToDeviceCluster(deviceData, deviceId, redisKey);
            } else {
                logger.info(">>>>> Device already exists in Redis, skipping...");
            }
        } catch (JsonProcessingException e) {
            logger.error(">>>>> Error parsing Redis value: " + redisVal, e);
        }
    }
}