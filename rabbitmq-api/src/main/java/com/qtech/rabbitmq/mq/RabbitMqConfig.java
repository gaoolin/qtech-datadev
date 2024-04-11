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
    public Queue wbComparisonResultQueue() {
        return new Queue("wbComparisonResultQueue", true);
    }

    @Bean
    public DirectExchange wbComparisonResultExchange() {
        return new DirectExchange("wbComparisonResultExchange", true, false);
    }

    @Bean
    public Binding wbComparisonResultBinding() {
        return new Binding("wbComparisonResultQueue", Binding.DestinationType.QUEUE, "wbComparisonResultExchange", "wbComparisonResultQueue", null);
    }
}
