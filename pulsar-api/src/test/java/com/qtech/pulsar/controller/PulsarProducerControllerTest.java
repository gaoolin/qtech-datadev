package com.qtech.pulsar.controller;

import com.qtech.common.utils.HttpConnectUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/11 11:33:54
 * desc   :  TODO
 */

@SpringBootTest
@RunWith(SpringRunner.class)
class PulsarProducerControllerTest {

    @Autowired
    PulsarProducerController<String> pulsarProducerController;

    @Test
    void topicProducer() {
        String s1 = HttpConnectUtils.post("http://10.170.6.40:32140/pulsar/api/sendString", "hello pulsar");
        System.out.println(s1);
    }
}
