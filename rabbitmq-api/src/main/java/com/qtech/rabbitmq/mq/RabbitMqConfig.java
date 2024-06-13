package com.qtech.rabbitmq.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 15:34:45
 * desc   :  配置类
 */

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue wbOlpCheckResultQueue() {
        return new Queue("wbOlpCheckResultQueue", true);
    }

    @Bean
    public DirectExchange wbOlpCheckResultExchange() {
        return new DirectExchange("wbOlpCheckResultExchange", true, false);
    }

    @Bean
    public Binding wbOlpCheckResultBinding() {
        return new Binding("wbOlpCheckResultQueue", Binding.DestinationType.QUEUE, "wbOlpCheckResultExchange", "wbOlpCheckResultQueue", null);
    }
}
