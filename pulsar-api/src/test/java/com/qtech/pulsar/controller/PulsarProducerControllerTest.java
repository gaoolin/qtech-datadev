package com.qtech.pulsar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.common.utils.HttpConnectUtils;
import com.qtech.common.utils.Utils;
import com.qtech.pulsar.common.Constants;
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
    private final ObjectMapper objectMapper = new ObjectMapper(); // 使用单例模式
    @Test
    void topicProducer() throws JsonProcessingException {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", Constants.MESSAGE);
        String s2 = objectMapper.writeValueAsString(map);
        JSONObject s = JSONObject.parseObject(JSON.toJSONString(map));
        System.out.println(s);
        String s1 = HttpConnectUtils.post("http://10.170.6.40:32140/pulsar/api/sendString", s2);
        System.out.println(s1);
    }
}
