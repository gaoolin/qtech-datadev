package com.qtech.pulsar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.common.utils.HttpConnectUtils;
import com.qtech.pulsar.common.Constants;

import java.util.HashMap;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/15 15:16:47
 * desc   :  TODO
 */


public class Test {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", Constants.MESSAGE);
        JSONObject s = JSONObject.parseObject(JSON.toJSONString(map));
        System.out.println(s);
        String s1 = HttpConnectUtils.post("http://10.170.6.40:31920/pulsar/api/sendString", s);
        System.out.println(s1);
    }
}
