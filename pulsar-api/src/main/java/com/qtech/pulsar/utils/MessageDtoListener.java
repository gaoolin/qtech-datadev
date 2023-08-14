package com.qtech.pulsar.utils;

import com.qtech.pulsar.pojo.MessageDto;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/14 09:19:14
 * desc   :  监听器
 */


public class MessageDtoListener implements MessageListener<MessageDto> {

    private static final Logger logger = LoggerFactory.getLogger(MessageDtoListener.class);

    @Override
    public void received(Consumer<MessageDto> consumer, Message<MessageDto> msg) {

        logger.info("接收到实体类：{}", msg.getValue());
        try {
            consumer.acknowledge(msg);
        } catch (PulsarClientException e) {
            consumer.negativeAcknowledge(msg);
        }
    }

    @Override
    public void reachedEndOfTopic(Consumer<MessageDto> consumer) {

    }
}
