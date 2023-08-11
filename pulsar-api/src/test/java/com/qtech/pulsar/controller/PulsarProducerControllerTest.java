package com.qtech.pulsar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
    PulsarProducerController pulsarProducerController;

    @Test
    void topicProducer() {
        JSONObject s = JSONObject.parseObject(JSON.toJSONString("hello pulsar"));
        String s1 = Utils.connectPost("http://10.170.6.40:32140/pulsar/api/topicProducer", s);
        System.out.println(s1);
    }
}
