package com.qtech.check.utils;

import com.alibaba.fastjson.JSON;
import com.qtech.check.pojo.AaListParams;
import com.qtech.check.pojo.AaListParamsStdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

    @Autowired
    private RedisTemplate<String, AaListParams> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 假设你有一个Map<String, AaListParams>，其中key是对象的唯一标识
    Map<String, AaListParams> messages = new HashMap<>();

    // 将AaListParamsMessage对象存入哈希
    public void saveMessagesToHash(Map<String, AaListParams> messages, String hashKey) {
        for (Map.Entry<String, AaListParams> entry : messages.entrySet()) {
            String messageId = entry.getKey();
            AaListParams message = entry.getValue();
            redisTemplate.opsForHash().put(hashKey, messageId, JSON.toJSONString(message));
        }
    }

    // 从哈希中获取单个AaListParamsMessage对象
    public AaListParams getMessageFromHash(String hashKey, String messageId) {
        String jsonString = (String) redisTemplate.opsForHash().get(hashKey, messageId);
        if (jsonString != null) {
            return JSON.parseObject(jsonString, AaListParams.class);
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
            messages.put(messageId, JSON.parseObject(jsonString, AaListParams.class));
        }
        return messages;
    }

    // 假设你有一个AaListParamsMessage对象，其名字为name
    public void saveMessage(String name, AaListParamsStdModel message) {
        stringRedisTemplate.opsForValue().set(name, JSON.toJSONString(message));
    }

    // 根据名字获取AaListParamsStdModel对象
    public AaListParamsStdModel getMessage(String name) {
        String jsonString = stringRedisTemplate.opsForValue().get(name);
        if (jsonString != null) {
            return JSON.parseObject(jsonString, AaListParamsStdModel.class);
        }
        return null;
    }

    private static void processEmptyStrings(Object obj) {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    try {
                        if (field.getType().equals(String.class) && field.get(obj).equals("")) {
                            field.set(obj, null);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Error processing empty strings", e);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
