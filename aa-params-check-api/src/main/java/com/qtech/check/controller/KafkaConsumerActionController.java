package com.qtech.check.controller;

import com.qtech.check.kafka.AaListParamsParseMessageCommonConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 21:24:44
 * desc   :
 */

@RestController
@RequestMapping("/im/kafka/action")
public class KafkaConsumerActionController {

    @Autowired
    private AaListParamsParseMessageCommonConsumer kafkaConsumer;

    @PostMapping("/stop-consumer")
    public void stopConsumerEndpoint() {
        kafkaConsumer.stopConsumer();
    }

    @PostMapping("/start-consumer")
    public void startConsumerEndpoint() {
        kafkaConsumer.startConsumer();
    }
}
