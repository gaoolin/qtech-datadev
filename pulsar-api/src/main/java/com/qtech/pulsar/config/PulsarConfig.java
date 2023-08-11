package com.qtech.pulsar.config;

import com.qtech.pulsar.pojo.MessageDto;
import io.github.majusko.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/08 09:10:58
 * desc   :  消息生产者配置
 */

@Configuration
public class PulsarConfig {
    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
                // topic
                .addProducer("aaList", String.class)
                .addProducer("xx", MessageDto.class);
    }
}
