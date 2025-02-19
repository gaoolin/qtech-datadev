package com.qtech.check.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.check.pojo.AaListParamsParsed;
import com.qtech.share.aa.pojo.ImAaListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * <p>
 * 在某些场景下，仍然需要通过配置类来补充和定制化 Redis 配置
 * 1. 扩展性和细粒度控制
 * 2. 默认 Bean 的覆盖
 * 3. 多环境支持
 */

@Configuration
public class RedisConfig {
    @Autowired
    @Qualifier("dateFormat2ObjectMapper")
    private ObjectMapper dateFormat2ObjectMapper;

    @Autowired
    @Qualifier("serializationObjectMapper")
    private ObjectMapper serializationObjectMapper;

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
        jacksonSeial.setObjectMapper(serializationObjectMapper);

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
    public RedisTemplate<String, ImAaListParams> aaListParamsStdModelRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ImAaListParams> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 设置 Key 的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // 设置 Value 的序列化方式
        Jackson2JsonRedisSerializer<ImAaListParams> serializer = new Jackson2JsonRedisSerializer<>(ImAaListParams.class);
        serializer.setObjectMapper(dateFormat2ObjectMapper);

        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}