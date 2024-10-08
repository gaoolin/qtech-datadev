package com.qtech.service.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qtech.service.entity.EqReverseCtrlInfo;
import com.qtech.service.entity.OcrLabelInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/14 10:04:35
 * desc   :  redis配置类
 */

@Configuration
public class RedisConfig {
    // 只针对EqReverseCtrlInfo实体对象的序列化配置，默认序列化方式。
    // 其他的实体类的序列化方式，需要单独配置。

    @Bean
    public RedisTemplate<String, EqReverseCtrlInfo> eqReverseCtrlInfoRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, EqReverseCtrlInfo> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer<EqReverseCtrlInfo> serializer = new Jackson2JsonRedisSerializer<>(EqReverseCtrlInfo.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        serializer.setObjectMapper(objectMapper);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        template.afterPropertiesSet();

        return template;
    }

    // 示例：为其他类型的实体类配置不同的RedisTemplate
    @Bean
    public RedisTemplate<String, OcrLabelInfo> otherEntityRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, OcrLabelInfo> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer<OcrLabelInfo> serializer = new Jackson2JsonRedisSerializer<>(OcrLabelInfo.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // 可以根据需要调整ObjectMapper的配置
        serializer.setObjectMapper(objectMapper);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        template.afterPropertiesSet();

        return template;
    }

    // 存储simId的忽略状态
    @Bean
    public RedisTemplate<String, Boolean> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Boolean> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // 设置键的序列化器为 StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());

        // 设置值的序列化器为 GenericToStringSerializer<Boolean>
        template.setValueSerializer(new GenericToStringSerializer<>(Boolean.class));
        return template;
    }
}
