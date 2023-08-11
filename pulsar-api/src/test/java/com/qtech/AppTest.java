package com.qtech;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "hello pulsar");
        JSONObject s = JSONObject.parseObject(JSON.toJSONString(map));
        for (int i = 0; i < 1000; i++) {

            String s1 = Utils.connectPost("http://10.170.6.40:32140/pulsar/api/topicProducer", s);
            System.out.println(s1);
        }
    }
}
