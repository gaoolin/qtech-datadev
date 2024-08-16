package com.qtech.rabbitmq.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.rabbitmq.domain.EqReverseCtrlInfo;
import com.qtech.rabbitmq.service.IEqReverseCtrlInfoService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/16 08:33:27
 * desc   :  设备反控消费消费者
 */

@Slf4j
@Component
public class EqReverseCtrlInfoQueueConsumer {

    private final IEqReverseCtrlInfoService eqReverseCtrlInfoService;
    private final ObjectMapper objectMapper;

    public EqReverseCtrlInfoQueueConsumer(IEqReverseCtrlInfoService eqReverseCtrlInfoService, ObjectMapper objectMapper) {
        this.eqReverseCtrlInfoService = eqReverseCtrlInfoService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "eqReverseCtrlInfoQueue", ackMode = "MANUAL")
    public void receive(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            List<EqReverseCtrlInfo> messages = validateAndParseMessage(msg, channel, deliveryTag);
            int oracleCnt = eqReverseCtrlInfoService.upsertOracle(messages);
            int dorisCnt = eqReverseCtrlInfoService.upsertDoris(messages);
            int dorisAddCnt = eqReverseCtrlInfoService.addBatchDoris(messages);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("处理消息失败: {} - Error: {}", msg, e.getMessage(), e);
            handleException(channel, deliveryTag, e);
        }
    }

    private List<EqReverseCtrlInfo> validateAndParseMessage(String msg, Channel channel, long deliveryTag) throws JsonProcessingException {
        try {
            // 使用Jackson解析JSON字符串为List<EqReverseCtrlInfo>
            List<EqReverseCtrlInfo> messages = null;
            // 增加JSON解析异常处理，适应Fastjson2
            try {
                // 如果不是数组，则解析为单个对象
                EqReverseCtrlInfo singleMessage = objectMapper.readValue(msg, EqReverseCtrlInfo.class);
                // 将单个对象封装成列表
                messages = Collections.singletonList(singleMessage);
            } catch (JsonMappingException e) {
                // 尝试直接解析为列表
                messages = objectMapper.readValue(msg, new TypeReference<List<EqReverseCtrlInfo>>() {
                });
            } catch (IOException e) {
                // 处理其他异常
                log.error("JSON解析失败, msg: {}", msg, e);
            }
            if (messages == null || messages.isEmpty()) {
                log.error("解析结果为空, msg: {}", msg);
            }
            return messages;
        } catch (JsonProcessingException e) {
            log.error("JSON解析失败, msg: {}", msg, e);
            throw e;
        }
    }

    private void handleException(Channel channel, long deliveryTag, Exception e) throws IOException {
        // 根据异常类型和业务需求决定是否重新入队或采取其他措施
        channel.basicReject(deliveryTag, true); // 示例中选择重新入队
    }
}