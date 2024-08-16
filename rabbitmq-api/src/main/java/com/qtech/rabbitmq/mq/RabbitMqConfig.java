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
    public DirectExchange qtechImExchange() {
        return new DirectExchange("qtechImExchange", true, false);
    }

    @Bean
    public Queue eqRevserCtrlInfoQueue() {
        return new Queue("eqReverseCtrlInfoQueue", true);
    }

    @Bean
    public Queue aaListParamsParsedQueue() {
        return new Queue("aaListParamsParsedQueue", true);
    }

    @Bean
    public Binding wbOlpCheckResultBinding() {
        return new Binding("eqReverseCtrlInfoQueue", Binding.DestinationType.QUEUE, "qtechImExchange", "eqReverseCtrlInfoQueue", null);
    }

    @Bean
    public Binding aaListParamsParsedBinding() {
        return new Binding("aaListParamsParsedQueue", Binding.DestinationType.QUEUE, "qtechImExchange", "aaListParamsParsedQueue", null);
    }
}
