package com.qtech.check.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qtech.check.pojo.AaListParamsParsed;
import com.qtech.check.pojo.AaListParamsStdModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/10 13:50:27
 * desc   :
 *
 * 在某些场景下，仍然需要通过配置类来补充和定制化 Redis 配置
 * 1. 扩展性和细粒度控制
 * 2. 默认 Bean 的覆盖
 * 3. 多环境支持
 */

@Configuration
public class RedisConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        clusterConfig.addClusterNode(new RedisNode("10.170.6.24", 6379));
        clusterConfig.addClusterNode(new RedisNode("10.170.6.25", 6379));
        clusterConfig.addClusterNode(new RedisNode("10.170.6.26", 6379));
        clusterConfig.addClusterNode(new RedisNode("10.170.6.141", 6379));
        clusterConfig.addClusterNode(new RedisNode("10.170.6.142", 6379));
        clusterConfig.addClusterNode(new RedisNode("10.170.1.68", 6379));
        clusterConfig.setPassword("im@2024"); // 添加密码
        clusterConfig.setMaxRedirects(3);

        LettuceConnectionFactory factory = new LettuceConnectionFactory(clusterConfig);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public RedisTemplate<String, AaListParamsParsed> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, AaListParamsParsed> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer<AaListParamsParsed> jacksonSeial = new Jackson2JsonRedisSerializer<>(AaListParamsParsed.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        template.setValueSerializer(jacksonSeial);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name = "genericRedisTemplate")
    public RedisTemplate<String, Object> genericRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean(name = "stringRedisTemplate")
    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }

        @Bean
    public RedisTemplate<String, AaListParamsStdModel> aaListParamsStdModelRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, AaListParamsStdModel> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 设置 Key 的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // 设置 Value 的序列化方式
        Jackson2JsonRedisSerializer<AaListParamsStdModel> serializer = new Jackson2JsonRedisSerializer<>(AaListParamsStdModel.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        serializer.setObjectMapper(objectMapper);

        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}