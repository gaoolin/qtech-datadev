package com.qtech.pulsar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.common.utils.HttpConnectUtils;
import com.qtech.common.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

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
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "hello pulsar");
        JSONObject s = JSONObject.parseObject(JSON.toJSONString(map));
        System.out.println(s);
        String s1 = HttpConnectUtils.post("http://10.170.6.40:32140/pulsar/api/sendString", s);
        System.out.println(s1);
    }
}
