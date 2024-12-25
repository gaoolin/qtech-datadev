package com.qtech.check.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.check.pojo.AaListParamsParsed;
import com.qtech.check.pojo.AaListParamsStdModel;
import com.qtech.check.pojo.AaListParamsStdModelInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/20 16:46:09
 * desc   :
 */

@Component
public class RedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    // 假设你有一个Map<String, AaListParamsParsed>，其中key是对象的唯一标识
    Map<String, AaListParamsParsed> messages = new HashMap<>();
    @Autowired
    private RedisTemplate<String, AaListParamsParsed> redisTemplate;
    @Autowired
    private RedisTemplate<String, AaListParamsStdModel> aaListParamsStdModelRedisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    // 将AaListParamsMessage对象存入哈希
    public void saveMessagesToHash(Map<String, AaListParamsParsed> messages, String hashKey) {
        for (Map.Entry<String, AaListParamsParsed> entry : messages.entrySet()) {
            String messageId = entry.getKey();
            AaListParamsParsed message = entry.getValue();
            try {
                redisTemplate.opsForHash().put(hashKey, messageId, objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                logger.error(">>>>> JSON解析失败, msg: {}", message, e);
            }
        }
    }

    // 从哈希中获取单个AaListParamsMessage对象
    public AaListParamsParsed getMessageFromHash(String hashKey, String messageId) {
        String jsonString = (String) redisTemplate.opsForHash().get(hashKey, messageId);
        if (jsonString != null) {
            try {
                return objectMapper.readValue(jsonString, AaListParamsParsed.class);
            } catch (JsonProcessingException e) {
                logger.error(">>>>> JSON解析失败, msg: {}", jsonString, e);
            }
        }
        return null;
    }

    // 获取哈希中的所有AaListParamsMessage对象
    public Map<String, AaListParamsParsed> getAllMessagesFromHash(String hashKey) {
        Map<Object, Object> values = redisTemplate.opsForHash().entries(hashKey);
        Map<String, AaListParamsParsed> messages = new HashMap<>();
        for (Map.Entry<Object, Object> entry : values.entrySet()) {
            String messageId = (String) entry.getKey();
            String jsonString = (String) entry.getValue();
            try {
                messages.put(messageId, objectMapper.readValue(jsonString, AaListParamsParsed.class));
            } catch (JsonProcessingException e) {
                logger.error(">>>>> JSON解析失败, msg: {}", jsonString, e);
            }
        }
        return messages;
    }

    // 假设你有一个AaListParamsMessage对象，其名字为name
    public void saveAaListParamsStdModel(String name, AaListParamsStdModel message) {
        aaListParamsStdModelRedisTemplate.opsForValue().set(name, message);
    }

    public void saveAaListParamsStdModelInfo(String name, AaListParamsStdModelInfo message) {
        try {
            stringRedisTemplate.opsForValue().set(name, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            logger.error(">>>>> JSON解析失败, msg: {}", message, e);
        }
    }

    // 根据名字获取AaListParamsStdModel对象
    public AaListParamsStdModel getAaListParamsStdModel(String name) {
        return aaListParamsStdModelRedisTemplate.opsForValue().get(name);
    }

    public AaListParamsStdModelInfo getAaListParamsStdModelInfo(String name) {
        String jsonString = stringRedisTemplate.opsForValue().get(name);
        if (jsonString != null) {
            try {
                return objectMapper.readValue(jsonString, AaListParamsStdModelInfo.class);
            } catch (JsonProcessingException e) {
                logger.error(">>>>> JSON解析失败, msg: {}", jsonString, e);
            }
        }
        return null;
    }
}
