package com.qtech.pulsar.utils;

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
 * date   :  2023/08/14 09:16:24
 * desc   :  监听器
 */

//@Component
public class StringMessageListener implements MessageListener<String> {

    private static final Logger logger = LoggerFactory.getLogger(StringMessageListener.class);

    @Override
    public void received(Consumer<String> consumer, Message<String> msg) {

        logger.info("接收到String类型消息：{}", msg.getValue());

        try {
            consumer.acknowledge(msg);
        } catch (PulsarClientException e) {
            consumer.negativeAcknowledge(msg);
        }
    }

    @Override
    public void reachedEndOfTopic(Consumer<String> consumer) {

    }
}
