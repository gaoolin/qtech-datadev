package com.qtech.message.config;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static com.qtech.message.utils.Constant.HEX_STR;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/24 09:54:26
 * desc   :
 */

@RunWith(SpringRunner.class)
// @SpringBootTest
class AaKafKaProducerTestTest {

    private static final Logger logger = LoggerFactory.getLogger(AaKafKaProducerTestTest.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void send() {
        String message = String.format("{'OpCode': '864735050116166', 'WoCode': 'C3PS66#', 'FactoryName': '%s'}", HEX_STR);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("qtech_im_aa_list_topic", message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info("send-消息发送成功");
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("消息发送失败：", ex);
                // 这里可以添加更多的异常处理逻辑，例如消息重试机制
            }
        });
    }
}