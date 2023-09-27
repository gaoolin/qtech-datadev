package com.qtech.pulsar.utils;

import com.qtech.common.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/11 13:45:09
 * desc   :
 */

//@Component
public class PulsarConsume {

    private static final Logger logger = LoggerFactory.getLogger(PulsarConsume.class);

    @Value("${pulsar.serviceUrl}")
    private String url;
    @Value("${pulsar.topicMap.aaList}")
    private String topic;
    @Value("${pulsar.subMap.aaList}")
    private String subscription;


    private PulsarClient client = null;
    private Consumer consumer = null;

    /**
     * 使用@PostConstruct注解用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
     */
    @PostConstruct
    public void initPulsar() throws Exception {
        try {
            //构造Pulsar client
            client = PulsarClient.builder()
                    .serviceUrl(url)
                    .build();

            //创建consumer
            consumer = client.newConsumer()
                    .topic(topic.split(","))
                    .subscriptionName(subscription)
                    .subscriptionType(SubscriptionType.Shared)  // 指定消费模式，包含：Exclusive，Failover，Shared，Key_Shared。默认Exclusive模式
                    .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)  // 指定从哪里开始消费还有Latest，valueof可选，默认Latest
                    .negativeAckRedeliveryDelay(60, TimeUnit.SECONDS)  // 指定消费失败后延迟多久broker重新发送消息给consumer，默认60s
                    .subscribe();

            // 开始消费
            new Thread(() -> {
                PulsarConsume alarmPulsarConsumer = SpringUtils.getBean(PulsarConsume.class);
                try {
                    alarmPulsarConsumer.start();
                } catch (Exception e) {
                    logger.error("消费Pulsar数据异常，停止Pulsar连接：", e);
                    alarmPulsarConsumer.close();
                }
            }).start();

        } catch (Exception e) {
            logger.error("Pulsar初始化异常：", e);
            throw e;
        }
    }

    private void start() throws Exception {
        //消费消息
        while (true) {
            Message message = consumer.receive();
            String[] keyArr = message.getKey().split("_");
            String jsons = new String(message.getData());

            if (StringUtils.isNotEmpty(jsons)) {
                try {
//                    wsSend(type, strategyId, camId, camTime, json, depId, alarmType, target);
                } catch (Exception e) {
                    logger.error("消费Pulsar数据异常，key【{}】，json【{}】：", message.getKey(), jsons, e);
                }
            }
            consumer.acknowledge(message);
        }
    }

    /**
     * 线程池异步处理Pulsar推送的数据
     *
     * @param camTime
     * @param type
     * @param camId
     * @param json
     */
//    @Async("threadPoolTaskExecutor")
    public void wsSend(Integer type, Integer strategyId, String camId, Long camTime, String json, Long depId, Integer alarmType, Integer target) {

    }


    public void close() {
        try {
            consumer.close();
        } catch (PulsarClientException e) {
            logger.error("关闭Pulsar消费者失败：", e);
        }
        try {
            client.close();
        } catch (PulsarClientException e) {
            logger.error("关闭Pulsar连接失败：", e);
        }
    }

}
