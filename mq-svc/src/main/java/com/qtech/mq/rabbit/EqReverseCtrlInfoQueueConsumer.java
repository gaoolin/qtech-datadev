package com.qtech.mq.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qtech.mq.domain.EqReverseCtrlInfo;
import com.qtech.mq.service.IEqReverseCtrlInfoService;
import com.rabbitmq.client.Channel;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/16 08:33:27
 * desc   :  设备反控消费消费者
 */

@Component
public class EqReverseCtrlInfoQueueConsumer implements InitializingBean, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(EqReverseCtrlInfoQueueConsumer.class);
    private final IEqReverseCtrlInfoService eqReverseCtrlInfoService;
    private final ObjectMapper objectMapper;
    private StatefulRedisClusterConnection<String, String> connection;

    public EqReverseCtrlInfoQueueConsumer(IEqReverseCtrlInfoService eqReverseCtrlInfoService) {
        this.eqReverseCtrlInfoService = eqReverseCtrlInfoService;
        this.objectMapper = createObjectMapper();
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .build();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        return mapper;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void destroy() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @RabbitListener(queues = "eqReverseCtrlInfoQueue", ackMode = "MANUAL")
    public void receive(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        if (msg == null || msg.isEmpty()) {
            logger.error(">>>>> 消息为空, eqReverseCtrlInfoQueue队列, msg: {}", msg);
            return;
        }
        try {
            List<EqReverseCtrlInfo> messages = validateAndParseMessage(msg, channel, deliveryTag);

            if (messages.isEmpty()) {
                logger.error(">>>>> 解析结果为空, msg: {}", msg);
                return;
            } else {
                for (EqReverseCtrlInfo message : messages) {
                    CompletableFuture<Integer> future = eqReverseCtrlInfoService.upsertOracleAsync(message);
                    CompletableFuture<Integer> dorisAsync = eqReverseCtrlInfoService.upsertDorisAsync(message);
                    CompletableFuture<Integer> addAaListDorisAsync = eqReverseCtrlInfoService.addAaListDorisAsync(message);
                    CompletableFuture<Void> allFuture = CompletableFuture.allOf(future, dorisAsync, addAaListDorisAsync).exceptionally(ex -> {
                        logger.error(">>>>> 异步处理消息时发生异常,消息可能持久化失败，应检查！: ", ex);
                        return null;
                    });
                    allFuture.thenRun(() -> {
                        try {
                            channel.basicAck(deliveryTag, false);
                        } catch (IOException e) {
                            logger.error("Failed to acknowledge message: {}", e.getMessage());
                        }
                    });
                }
            }
        } catch (Exception e) {
            logger.error(">>>>> 处理消息失败: {} - Error: {}", msg, e.getMessage(), e);
            handleException(channel, deliveryTag, e);
        }
    }


    private List<EqReverseCtrlInfo> validateAndParseMessage(String msg, Channel channel, long deliveryTag) throws JsonProcessingException {
        try {
            // 使用Jackson解析JSON字符串为List<EqReverseCtrlInfo>
            List<EqReverseCtrlInfo> messages = null;
            try {
                // 尝试解析为单个对象
                EqReverseCtrlInfo singleMessage = objectMapper.readValue(msg, EqReverseCtrlInfo.class);
                // 封装成列表
                messages = Collections.singletonList(singleMessage);
            } catch (JsonMappingException e) {
                // 尝试直接解析为列表
                messages = objectMapper.readValue(msg, new TypeReference<List<EqReverseCtrlInfo>>() {
                });
            }
            if (messages == null || messages.isEmpty()) {
                logger.error(">>>>> 解析结果为空, msg: {}", msg);
            }
            return messages;
        } catch (JsonProcessingException e) {
            logger.error(">>>>> JSON解析失败, msg: {}", msg, e);
            throw e;
        }
    }

    private void handleException(Channel channel, long deliveryTag, Exception e) throws IOException {
        if (e instanceof JsonProcessingException) {
            // JSON解析错误，可以考虑重新入队
            channel.basicNack(deliveryTag, false, true);
        } else {
            // 其他类型的异常，根据具体业务需求处理
            channel.basicReject(deliveryTag, false);
        }
    }
}