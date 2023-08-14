package com.qtech.pulsar.service.impl;

import com.qtech.pulsar.service.IPulsarProducerService;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/11 14:07:38
 * desc   :  TODO
 */

@Service
public class PulsarProducerServiceImpl<T> implements IPulsarProducerService<T> {

    private static final Logger logger = LoggerFactory.getLogger(PulsarProducerServiceImpl.class);

    @Override
    public int send(String topic, T msg) {
        return 0;
    }

    @Override
    public int sendAsync(String topic, T msg) {
        return 0;
    }

    /**
     * 异步发送一条消息
     *
     * @param message  消息体
     * @param producer 生产者实例
     */
    @Override
    public void sendAsyncMessage(T message, Producer<T> producer) {
        producer.sendAsync(message).thenAccept(msgId -> {
        });
        logger.info("消息已发");
    }

    /**
     * 同步发送一条消息
     *
     * @param message  消息体
     * @param producer 生产者实例
     * @throws PulsarClientException
     */
    @Override
    public void sendSyncMessage(T message, Producer<T> producer) {
        try {
            MessageId send = producer.send(message);
            logger.info("");
            logger.info("消息已发送：{}", send);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}
