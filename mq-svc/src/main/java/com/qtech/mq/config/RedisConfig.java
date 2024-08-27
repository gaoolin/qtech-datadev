package com.qtech.mq.config;

import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 08:47:46
 * desc   :
 */

@Configuration(proxyBeanMethods = false)
public class RedisConfig extends RedisAutoConfiguration {

}
