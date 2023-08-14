package com.qtech.pulsar.utils;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/14 08:31:04
 * desc   :  PulsarClient工具
 */


//@RestController
//@RequestMapping(value = "/pulsarclient/api")
public class PulsarClientController {

    @Value("${pulsar.serviceUrl}")
    String serverUrl;

    @RequestMapping(value = "client", method = RequestMethod.POST)
    public String client() {
        try {
            PulsarClient build = PulsarClient.builder()
                    .serviceUrl(serverUrl)
                    .build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }

        return null;
    }
}
