package com.qtech.olp.controller;

import com.qtech.olp.entity.AaListKafkaMessage;
import com.qtech.olp.processor.MessageProcessor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 13:38:56
 * desc   :
 */

@RestController
@RequestMapping("/aa/msg")
public class MsgReceiveController {

    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    public void receive() {
        System.out.println("receive");
    }

    @RequestMapping(value = "receive", method = RequestMethod.POST)
    public void receivePost(@RequestBody String msg) {
        MessageProcessor messageProcessor = new MessageProcessor();
        AaListKafkaMessage aaListKafkaMessage = messageProcessor.processMessage(AaListKafkaMessage.class, msg);
        System.out.println(aaListKafkaMessage);
    }
}
