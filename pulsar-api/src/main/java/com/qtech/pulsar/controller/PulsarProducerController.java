package com.qtech.pulsar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.pulsar.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/07 16:54:10
 * desc   :  Pulsar生产者控制层
 */

@Api(tags = "PulsarProducerController", description = "Pulsar功能测试")
@RestController
@RequestMapping(value = "/pulsar/api")
public class PulsarProducerController<T> {

    private static final Logger logger = LoggerFactory.getLogger(PulsarProducerController.class);
/*

    @Autowired
    @Qualifier("aaList-byte-topic-producer")
    private Producer<byte[]> aaListByteProducer;
*/

    @Autowired
    @Qualifier("aaList-string-topic-producer")
    private Producer<String> aaListStringProducer;
/*

    @Autowired
    @Qualifier("aaList-messageDto-topic-producer")
    private Producer<MessageDto> aaListMessageDtoProducer;
*/

    @Autowired
    @Qualifier("aaList-string-topic-consumer")
    private Consumer<String> aaListStringConsumer;
/*

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendByte", method = RequestMethod.POST)
    public String sendMsg(@RequestBody byte[] msg) {
        try {
            aaListByteProducer.sendAsync(msg);
            logger.info(">>>> 消息已异步发送。");
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }
*/

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendString", method = RequestMethod.POST)
    public String sendMsg(@RequestBody String msg) {
        try {
            MessageId send = aaListStringProducer.send(msg);
            logger.info(">>>> 消息已同步发送， MessageId：" + send);
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }
/*

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendMessageDto", method = RequestMethod.POST)
    public String sendMsg(@RequestBody MessageDto msg) {
        try {
            aaListMessageDtoProducer.send(msg);
            logger.info(">>>> 实体类消息已异步发送");
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }
*/

    @ApiOperation("发送消息")
    @RequestMapping(value = "/receiveString", method = RequestMethod.POST)
    public String consume() {
        Message<String> msg = null;
        try {
            msg = aaListStringConsumer.receive();
            aaListStringConsumer.acknowledge(msg);
            String s = new String(msg.getData());
            logger.info(s);
            return s;
        } catch (PulsarClientException e) {
            e.printStackTrace();
            aaListStringConsumer.negativeAcknowledge(msg);
        }
        return null;
    }

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendtest", method = RequestMethod.POST)
    public String sendTest(@RequestBody String msg) {
        try {
            PulsarClient client = PulsarClient.builder()
                    .serviceUrl("pulsar://qtech-pulsar-broker.pulsar:6650")
                    .build();

            Producer<String> producer = client.newProducer(Schema.STRING)
                    .topic("persistent://qtech-datadev/qtech-eq-aa/aaList")
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

            producer.send(msg);
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }
}
