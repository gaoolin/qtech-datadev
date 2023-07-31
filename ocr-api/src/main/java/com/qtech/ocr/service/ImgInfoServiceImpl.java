package com.qtech.ocr.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/31 17:04:14
 * desc   :  TODO
 */

@Service
public class ImgInfoServiceImpl {

    public String cephGrwSvc(String fileName, String byteJson) {
        String DataUrl = "http://10.170.6.40:31555//cephgrw/api/uploadByte";

        JSONObject jsonObject1 = JSON.parseObject(byteJson);

        String s = Base64.encodeBase64String(jsonObject1);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("bucketName", "qtech-20230717");
        paramMap.put("fileName", "test8.jpg");
        paramMap.put("contents", s);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(paramMap));

        String jsonStr = Utils.connectPost(DataUrl, jsonObject);

        System.out.println(jsonStr);
    }
}
