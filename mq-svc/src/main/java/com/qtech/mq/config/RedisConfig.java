package com.qtech.mq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qtech.mq.domain.EqReverseCtrlInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 08:47:46
 * desc   :
 */


@Configuration
public class RedisConfig {

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

}