package com.qtech.pulsar.common;

import com.qtech.pulsar.pojo.PulsarProperties;
import org.apache.pulsar.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/14 09:14:21
 * desc   :  Pulsar的核心服务类
 */

@Component
public class PulsarCommon {

    private static final Logger logger = LoggerFactory.getLogger(PulsarCommon.class);

    @Autowired
    private PulsarProperties pulsarProperties;

    @Autowired
    private PulsarClient client;

    /**
     * 创建一个生产者
     *
     * @param topic  topic name
     * @param schema schema方式
     * @param <T>    泛型
     * @return Producer生产者
     */
    @PostConstruct
    public <T> Producer<T> producer(String topic, Schema<T> schema) {

        try {
            return client.newProducer(schema)
                    // 是否开启批量处理消息，默认true,需要注意的是enableBatching只在异步发送sendAsync生效，同步发送send失效。因此建议生产环境若想使用批处理，则需使用异步发送，或者多线程同步发送
                    .enableBatching(true)
                    // 消息压缩（四种压缩方式：LZ4，ZLIB，ZSTD，SNAPPY），consumer端不用做改动就能消费，开启后大约可以降低3/4带宽消耗和存储（官方测试）
                    .compressionType(CompressionType.LZ4)
                    // 设置将对发送的消息进行批处理的时间段,10ms；可以理解为若该时间段内批处理成功，则一个batch中的消息数量不会被该参数所影响。
                    .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                    // 设置发送超时0s；如果在sendTimeout过期之前服务器没有确认消息，则会发生错误。默认30s，设置为0代表无限制，建议配置为0
                    .sendTimeout(0, TimeUnit.SECONDS)
                    // 批处理中允许的最大消息数。默认1000
                    .batchingMaxMessages(1000)
                    // 设置等待接受来自broker确认消息的队列的最大大小，默认1000
                    .maxPendingMessages(1000)
                    // 设置当消息队列中等待的消息已满时，Producer.send 和 Producer.sendAsync 是否应该block阻塞。默认为false，达到maxPendingMessages后send操作会报错，设置为true后，send操作阻塞但是不报错。建议设置为true
                    .blockIfQueueFull(true)
                    // 向不同partition分发消息的切换频率，默认10ms，可根据batch情况灵活调整
                    .roundRobinRouterBatchingPartitionSwitchFrequency(10)
                    // key_Shared模式要用KEY_BASED,才能保证同一个key的message在一个batch里
                    .batcherBuilder(BatcherBuilder.DEFAULT)
                    .create();
        } catch (PulsarClientException e) {
            throw new RuntimeException("初始化Pulsar Producer失败");
        }
    }

    /**
     * @param topic           topic name
     * @param subscription    sub name
     * @param messageListener MessageListener的自定义实现类
     * @param schema          schema消费方式
     * @param <T>             泛型
     * @return Consumer消费者
     */
    @PostConstruct
    public <T> Consumer<T> consumer(String topic, String subscription,
                                    MessageListener<T> messageListener, Schema<T> schema) {
        try {
            return client.newConsumer(schema)
                    .topic(pulsarProperties.getCluster() + "/" + pulsarProperties.getNamespace() + "/" + topic)
                    .subscriptionName(subscription)
                    .ackTimeout(10, TimeUnit.SECONDS)
                    .subscriptionType(SubscriptionType.Shared)
                    .messageListener(messageListener)
                    .subscribe();
        } catch (PulsarClientException e) {
            throw new RuntimeException("初始化Pulsar Consumer失败");
        }
    }
}