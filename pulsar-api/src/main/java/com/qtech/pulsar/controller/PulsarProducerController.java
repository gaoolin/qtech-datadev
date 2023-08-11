package com.qtech.pulsar.controller;

import com.qtech.pulsar.common.Constants;
import com.qtech.pulsar.pojo.MessageDto;
import com.qtech.pulsar.service.IPulsarProducerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "PulsarProducerController", description = "Pulsar功能测试")
@RestController
@RequestMapping(value = "/pulsar/api")
public class PulsarProducerController<T> {

    @Autowired
    private IPulsarProducerService<T> pulsarProducerService;

    @Autowired
    private IPulsarProducerService<byte[]> stringProducerService;

    @Autowired
    private IPulsarProducerService<MessageDto> messageDtoProducerService;

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public String sendMsg(@RequestBody T msg) {
        try {
            pulsarProducerService.send(Constants.LOG_TOPIC, msg);
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendMsgString", method = RequestMethod.POST)
    public String sendMsg(@RequestBody String msg) {
        try {
            stringProducerService.send(Constants.LOG_TOPIC, msg.getBytes(StandardCharsets.UTF_8));
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendMsgMessageDto", method = RequestMethod.POST)
    public String sendMsg(@RequestBody MessageDto msg) {
        try {
            messageDtoProducerService.send(Constants.LOG_TOPIC, msg);
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }
}
