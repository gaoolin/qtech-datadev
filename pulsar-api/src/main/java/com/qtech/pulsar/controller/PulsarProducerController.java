package com.qtech.pulsar.controller;

import com.qtech.pulsar.common.Constants;
import io.github.majusko.pulsar.producer.PulsarTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;


/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/07 16:54:10
 * desc   :  Pulsar生产者控制层
 */

@RestController
@RequestMapping(value = "/pulsar/api")
public class PulsarProducerController {


    @Autowired
    private PulsarTemplate<byte[]> defaultProducer;

    @RequestMapping(value = "/topicProducer", method = RequestMethod.POST)
    public String topicProducer(@RequestBody String msg) {
        defaultProducer.sendAsync(Constants.LOG_TOPIC, msg.getBytes(StandardCharsets.UTF_8));
        return null;
    }
}
