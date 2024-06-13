package com.qtech.rabbitmq.mq;

import com.alibaba.fastjson2.JSON;
import com.qtech.rabbitmq.domain.WbOlpCheckResult;
import com.qtech.rabbitmq.service.IWbOlpCheckResultService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 15:32:02
 * desc   :  接受消息
 */

@Slf4j
@Component
public class WbOlpCheckResultQueueRabbitMqConsumer {

    @Autowired
    private IWbOlpCheckResultService wbComparisonResultService;

    @RabbitListener(queues = "wbOlpCheckResultQueue", ackMode = "MANUAL")
    public void receive(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            // 解析为数组
            WbOlpCheckResult[] wbOlpCheckResultsArray = JSON.parseArray(msg, WbOlpCheckResult.class).toArray(new WbOlpCheckResult[0]);

            for (WbOlpCheckResult wbOlpCheckResult : wbOlpCheckResultsArray) {
                log.info("接收到消息：" + wbOlpCheckResult);
                wbComparisonResultService.add(wbOlpCheckResult);
            }

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 处理异常，拒绝消息并重新入队或丢弃
            // channel.basicNack(deliveryTag, false, true); // true 表示重新入队
            // 或者使用
            channel.basicReject(deliveryTag, false); // false 表示丢弃
        }
    }
}
