package com.qtech.rabbitmq.conntroller;

import com.qtech.rabbitmq.mq.RabbitMqProducer;
import com.qtech.rabbitmq.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping(value = "/rabbitmq")
public class RabbitMqProducerController {

    @Autowired
    private RabbitMqProducer rabbitMqProducer;

    @RequestMapping(value = "/chk/result", method = RequestMethod.POST)
    public ApiResponse<String> sendMsg(@RequestBody String msg) {
        // 3个参数：交换机，路由键（队列名），消息。
        try {
            rabbitMqProducer.send("eqReverseCtrlInfoExchange", "eqReverseCtrlInfoQueue", msg);
            return ApiResponse.success();
        } catch (Exception e) {
            log.error("send msg error: {}", e.getMessage());
        }
        return ApiResponse.badRequest("send msg failed");
    }
}