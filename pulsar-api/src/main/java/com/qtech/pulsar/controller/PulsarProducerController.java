package com.qtech.pulsar.controller;

import com.qtech.pulsar.common.PulsarCommon;
import com.qtech.pulsar.pojo.MessageDto;
import com.qtech.pulsar.service.IPulsarProducerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.impl.schema.AvroSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


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

    @Autowired
    private IPulsarProducerService<byte[]> pulsarProducerService;

    @Autowired
    private IPulsarProducerService<String> stringProducerService;

    @Autowired
    private IPulsarProducerService<MessageDto> messageDtoIPulsarProducerService;

    @Autowired
    private PulsarCommon pulsarCommon;

    @Value("${pulsar.topicMap.aaList}")
    private String topic1;

    @Value("${pulsar.topicMap.aaList}")
    private String topic2;

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendByte", method = RequestMethod.POST)
    public String sendMsg(@RequestBody byte[] msg) {
        try {
            // msg.getBytes(StandardCharsets.UTF_8)
            pulsarProducerService.sendAsyncMessage(msg, pulsarCommon.createProducer(topic1, Schema.BYTES));
            logger.info(">>>> 消息已异步发送。");
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendString", method = RequestMethod.POST)
    public String sendMsg(@RequestBody String msg) {
        try {
            logger.info(topic1);
            stringProducerService.sendAsyncMessage(msg, pulsarCommon.createProducer(topic1, Schema.STRING));
            logger.info(">>>> 消息已异步发送。");
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    @ApiOperation("发送消息")
    @RequestMapping(value = "/sendMessageDto", method = RequestMethod.POST)
    public String sendMsg(@RequestBody MessageDto msg) {
        try {
            logger.info(topic2);
            messageDtoIPulsarProducerService.sendAsyncMessage(msg, pulsarCommon.createProducer(topic2, AvroSchema.of(MessageDto.class)));
            logger.info(">>>> 实体类消息已异步发送");
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
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
                    .create();

            producer.send("hello pulsar");
            producer.send(msg);
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }
}
