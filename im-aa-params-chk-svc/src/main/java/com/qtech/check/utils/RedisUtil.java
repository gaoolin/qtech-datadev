package com.qtech.check.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.check.pojo.AaListParams;
import com.qtech.check.pojo.AaListParamsStdModel;
import com.qtech.check.pojo.AaListParamsStdModelInfo;
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

    // 假设你有一个Map<String, AaListParams>，其中key是对象的唯一标识
    Map<String, AaListParams> messages = new HashMap<>();
    @Autowired
    private RedisTemplate<String, AaListParams> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private  ObjectMapper objectMapper;

    // 将AaListParamsMessage对象存入哈希
    public void saveMessagesToHash(Map<String, AaListParams> messages, String hashKey) {
        for (Map.Entry<String, AaListParams> entry : messages.entrySet()) {
            String messageId = entry.getKey();
            AaListParams message = entry.getValue();
            try {
                redisTemplate.opsForHash().put(hashKey, messageId, objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 从哈希中获取单个AaListParamsMessage对象
    public AaListParams getMessageFromHash(String hashKey, String messageId) {
        String jsonString = (String) redisTemplate.opsForHash().get(hashKey, messageId);
        if (jsonString != null) {
            try {
                return objectMapper.readValue(jsonString, AaListParams.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    // 获取哈希中的所有AaListParamsMessage对象
    public Map<String, AaListParams> getAllMessagesFromHash(String hashKey) {
        Map<Object, Object> values = redisTemplate.opsForHash().entries(hashKey);
        Map<String, AaListParams> messages = new HashMap<>();
        for (Map.Entry<Object, Object> entry : values.entrySet()) {
            String messageId = (String) entry.getKey();
            String jsonString = (String) entry.getValue();
            try {
                messages.put(messageId, objectMapper.readValue(jsonString, AaListParams.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return messages;
    }

    // 假设你有一个AaListParamsMessage对象，其名字为name
    public void saveAaListParamsStdModel(String name, AaListParamsStdModel message) {
        try {
            stringRedisTemplate.opsForValue().set(name, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAaListParamsStdModelInfo(String name, AaListParamsStdModelInfo message) {
        try {
            stringRedisTemplate.opsForValue().set(name, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 根据名字获取AaListParamsStdModel对象
    public AaListParamsStdModel getAaListParamsStdModel(String name) {
        String jsonString = stringRedisTemplate.opsForValue().get(name);
        if (jsonString != null) {
            try {
                return objectMapper.readValue(jsonString, AaListParamsStdModel.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public AaListParamsStdModelInfo getAaListParamsStdModelInfo(String name) {
        String jsonString = stringRedisTemplate.opsForValue().get(name);
        if (jsonString != null) {
            try {
                return objectMapper.readValue(jsonString, AaListParamsStdModelInfo.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}