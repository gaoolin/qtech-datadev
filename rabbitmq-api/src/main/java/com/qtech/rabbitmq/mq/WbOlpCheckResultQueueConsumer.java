package com.qtech.rabbitmq.mq;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
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
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 15:32:02
 * desc   :  接受消息
 */


@Slf4j
@Component
public class WbOlpCheckResultQueueConsumer {

    @Autowired
    private IWbOlpCheckResultService wbOlpCheckResultService;

    @RabbitListener(queues = "wbOlpCheckResultQueue", ackMode = "MANUAL")
    public void receive(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            // 增加JSON解析异常处理，适应Fastjson2
            List<WbOlpCheckResult> wbOlpCheckResultsList = validateAndParseMessage(msg, channel, deliveryTag);

            // 批量插入
            wbOlpCheckResultService.addBatch(wbOlpCheckResultsList);

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
        } catch (JSONException e) { // Fastjson2中的解析异常类为JSONException
            log.error("JSON解析错误: {} - Error: {}", msg, e.getMessage(), e);
            handleException(channel, deliveryTag, e);
        } catch (Exception e) {
            log.error("处理消息失败: {} - Error: {}", msg, e.getMessage(), e);
            handleException(channel, deliveryTag, e);
        }
    }

    private List<WbOlpCheckResult> validateAndParseMessage(String msg, Channel channel, long deliveryTag) throws JSONException {
        // 在Fastjson2中直接解析，如果有预处理或校验逻辑也可以在此处添加
        List<WbOlpCheckResult> resultList = JSON.parseArray(msg, WbOlpCheckResult.class);
        if (resultList == null || resultList.isEmpty()) {
            log.error("解析结果为空, msg: {}", msg);
        }
        return resultList;
    }

    private void handleException(Channel channel, long deliveryTag, Exception e) throws IOException {
        // 根据异常类型和业务需求决定是否重新入队或采取其他措施
        channel.basicReject(deliveryTag, true); // 示例中选择重新入队
    }
}