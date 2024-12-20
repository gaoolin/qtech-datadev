package com.qtech.mq.kafka.eqn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.qtech.mq.domain.DeviceData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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

    private static final Set<String> SPECIAL_DEVICE_TYPES_AA = new HashSet<>(Arrays.asList("LINUXRSAA"));

    private static final ObjectMapper objectMapper = configureObjectMapper();

    @Value("${kafka.target.topic}")
    private String targetTopic;

    @Autowired
    private KafkaTemplate<String, String> targetKafkaTemplate;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    public static ObjectMapper configureObjectMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).enable(MapperFeature.AUTO_DETECT_FIELDS);
    }

    @KafkaListener(topics = "${kafka.source.topic}", containerFactory = "sourceKafkaListenerContainerFactory")
    public void consumeAndForward(String message) {
        try {
            DeviceData deviceData = parseMessage(message);
            String deviceType = deviceData.getDeviceType().toUpperCase();
            String deviceId = deviceData.getDeviceId();

            // if ("6841".equals(deviceId)) {
            //     logger.info(">>>>> Received message with ID: {}", deviceId);
            //     return;
            // }

            logger.debug(">>>>> Received message with ID: {}", deviceId);

            // if (!VALID_DEVICE_TYPES.contains(deviceType)) {
            //     logger.info(">>>>> Invalid device type: " + deviceType);
            //     return;
            // }

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
        if (SPECIAL_DEVICE_TYPES_AA.contains(deviceType) && remoteControlEq == null) {
            logger.debug(">>>>> Device type " + deviceType + " does not support remote control.");
            deviceData.setRemoteControl("2");
        } else if (remoteControlEq == null) {
            deviceData.setRemoteControl("999");
        } else if ("WB".equals(deviceType)) {
            if (!remoteControlEq.matches("\\d+")) {
                deviceData.setRemoteControl("999");
            }
        }

        if (StringUtils.isEmpty(deviceData.getStatus())) {
            deviceData.setStatus("999");
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
            logger.debug(">>>>> Forwarded message to target cluster: " + deviceDataJson);
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
                logger.debug(">>>>> Device already exists in Redis, skipping...");
            }
        } catch (JsonProcessingException e) {
            logger.error(">>>>> Error parsing Redis value: " + redisVal, e);
        }
    }
}