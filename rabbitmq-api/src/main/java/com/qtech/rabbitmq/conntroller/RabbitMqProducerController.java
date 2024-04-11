package com.qtech.rabbitmq.conntroller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.qtech.rabbitmq.domain.WbComparisonResult;
import com.qtech.rabbitmq.mq.RabbitMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 15:44:49
 * desc   :  生产者控制器
 */

@RestController
@RequestMapping(value = "/rabbitmq/msg")
public class RabbitMqProducerController {

    @Autowired
    private RabbitMqProducer rabbitMqProducer;

    @RequestMapping(value = "/wbComparison", method = RequestMethod.POST)
    public String sendMsg(@RequestBody WbComparisonResult wbComparisonResult) {
        // 对象转换为json字符串
        String jsonString = JSON.toJSONString(wbComparisonResult);
        // 3个参数：交换机，路由键（队列名），消息。
        rabbitMqProducer.send("wbComparisonResultExchange", "wbComparisonResultQueue", jsonString);
        return "ok!";
    }
}
