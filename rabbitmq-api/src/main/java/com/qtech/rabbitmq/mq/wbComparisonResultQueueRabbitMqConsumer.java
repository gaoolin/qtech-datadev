package com.qtech.rabbitmq.mq;

import com.alibaba.fastjson2.JSON;
import com.qtech.rabbitmq.domain.WbComparisonResult;
import com.qtech.rabbitmq.service.IWbComparisonResultService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 15:32:02
 * desc   :  接受消息
 */

@Component
public class wbComparisonResultQueueRabbitMqConsumer {

    @Autowired
    private IWbComparisonResultService wbComparisonResultService;
    @RabbitListener(queues = "wbComparisonResultQueue")
    public void receive(String msg) {
        WbComparisonResult wbComparisonResult = JSON.parseObject(msg, WbComparisonResult.class);
        wbComparisonResultService.add(wbComparisonResult);
    }
}
