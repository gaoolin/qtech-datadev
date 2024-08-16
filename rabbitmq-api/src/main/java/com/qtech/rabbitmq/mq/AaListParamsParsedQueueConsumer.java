package com.qtech.rabbitmq.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.rabbitmq.domain.AaListParams;
import com.qtech.rabbitmq.service.IAaListParamsParsedService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/16 14:01:32
 * desc   :
 */

@Slf4j
@Component
public class AaListParamsParsedQueueConsumer {
    private final ObjectMapper objectMapper;
    private final IAaListParamsParsedService aaListParamsParsedService;

    public AaListParamsParsedQueueConsumer(IAaListParamsParsedService aaListParamsParsedService, ObjectMapper objectMapper) {
        this.aaListParamsParsedService = aaListParamsParsedService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "aaListParamsParsedQueue", ackMode = "MANUAL")
    public void receive(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws Exception {
        log.info("receive aaListParamsParsedQueue message: {}", msg);
        try {
            AaListParams singleMessage = validateAndParseMessage(msg, channel, deliveryTag);
            int cnt = aaListParamsParsedService.save(singleMessage);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("receive aaListParamsParsedQueue message error: {}", e.getMessage(), e);
            channel.basicNack(deliveryTag, false, false);
        }
    }

    private AaListParams validateAndParseMessage(String msg, Channel channel, long deliveryTag) throws JsonProcessingException {
        try {
            AaListParams singleMessage = null;
            // 增加JSON解析异常处理，适应Fastjson2
            try {
                // 如果不是数组，则解析为单个对象
                singleMessage = objectMapper.readValue(msg, AaListParams.class);
            } catch (JsonMappingException e) {
                // 尝试直接解析为列表
                objectMapper.readValue(msg, new TypeReference<List<AaListParams>>() {
                });
                log.error("解析结果为列表, msg: {}", msg);
            } catch (IOException e) {
                // 处理其他异常
                log.error("JSON解析失败, msg: {}", msg, e);
            }
            if (singleMessage == null) {
                log.error("解析结果为空, msg: {}", msg);
            }
            return singleMessage;
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
