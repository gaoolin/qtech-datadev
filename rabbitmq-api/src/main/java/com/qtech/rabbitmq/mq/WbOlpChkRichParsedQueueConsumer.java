package com.qtech.rabbitmq.mq;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.qtech.rabbitmq.domain.WbOlpCheckResult;
import com.qtech.rabbitmq.domain.WbOlpChkRichParsed;
import com.qtech.rabbitmq.service.IWbOlpChkRichParsedService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/15 20:35:42
 * desc   :
 */

@Slf4j
@Component
public class WbOlpChkRichParsedQueueConsumer {

    @Autowired
    private IWbOlpChkRichParsedService wbOlpChkRichParsedService;

    // @RabbitListener(queues = "wbOlpChkRichParsedQueue", ackMode = "MANUAL")
    public void receive(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {

        try {
            List<WbOlpChkRichParsed> wbOlpChkRichParseds = validateAndParseMessage(msg);
            wbOlpChkRichParsedService.addBatch(wbOlpChkRichParseds);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            handleException(channel, deliveryTag, e);
        }

    }

    private List<WbOlpChkRichParsed> validateAndParseMessage(String msg) {
        // 消息验证和解析逻辑
        List<WbOlpChkRichParsed> resultList = JSON.parseArray(msg, WbOlpChkRichParsed.class);
                if (resultList == null || resultList.isEmpty()) {
            log.error("解析结果为空, msg: {}", msg);
        }
        return resultList;
    }

    private void handleException(Channel channel, long deliveryTag, Exception e) throws IOException {
        // 根据异常类型和业务需求决定是否重新入队或采取其他措施
        channel.basicReject(deliveryTag, true); // 选择重新入队
    }
}
