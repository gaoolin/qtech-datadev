package com.qtech.pulsar.config;

import com.qtech.pulsar.pojo.PulsarProperties;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/08 09:10:58
 * desc   :  消息生产者配置
 */

/*@Configuration
public class PulsarConfig {

    @Autowired
    PulsarProperties pulsarProperties;

    @Value("${pulsar.serviceUrl}")
    String serviceUrl;

    @Bean
    public PulsarClient pulsarClient() {

        try {
            return PulsarClient.builder()
                    .authentication(AuthenticationFactory.token(pulsarProperties.getToken()))
                    .serviceUrl(pulsarProperties.getServiceUrl())
                    .build();
        } catch (PulsarClientException e) {
            System.out.println(e);
            throw new RuntimeException("初始化Pulsar Client失败");
        }
    }
}*/
