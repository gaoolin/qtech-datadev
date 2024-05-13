package com.qtech.olp.controller;

import io.github.majusko.pulsar.annotation.PulsarConsumer;
import io.github.majusko.pulsar.constant.Serialization;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/10 15:30:43
 * desc   :
 */


public class PulsarConsumeController {
    @PulsarConsumer(topic = "aaList",  // 订阅topic名称
            subscriptionName = "subs-aaList", // 订阅名称
            serialization = Serialization.JSON, // 序列化方式
            subscriptionType = SubscriptionType.Shared, // 订阅模式，默认为独占模式
            consumerName = "aaList-Consumer-api", // 消费者名称
            maxRedeliverCount = 3, // 最大重试次数
            deadLetterTopic = "topic1-DLQ" // 死信topic名称
    )
    @RequestMapping(value = "/topicConsume", method = RequestMethod.GET)
    public void topicConsume(byte[] msg) throws Exception {
        // TODO process your message

        String message = new String(msg);
        System.out.println("Received a new message. content: " + message);
        // 如果消费失败，请抛出异常，这样消息会进入重试队列，之后可以重新消费，直到达到最大重试次数之后，进入死信队列。前提是要创建重试和死信topic
    }
}
