package com.qtech.rabbitmq.conntroller;

import com.alibaba.fastjson2.JSON;
import com.qtech.rabbitmq.domain.WbOlpCheckResult;
import com.qtech.rabbitmq.mq.RabbitMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 15:44:49
 * desc   :  生产者控制器
 */

@RestController
@RequestMapping(value = "/rabbitmq/msg")
public class WbOlpCheckProducerController {

    @Autowired
    private RabbitMqProducer rabbitMqProducer;

    @RequestMapping(value = "/wbOlpCheck", method = RequestMethod.POST)
    public String sendMsg(@RequestBody WbOlpCheckResult wbOlpCheckResult) {
        // 对象转换为json字符串
        String jsonString = JSON.toJSONString(wbOlpCheckResult);
        // 3个参数：交换机，路由键（队列名），消息。
        rabbitMqProducer.send("wbOlpCheckResultExchange", "wbOlpCheckResultQueue", jsonString);
        return "ok!";
    }
}
