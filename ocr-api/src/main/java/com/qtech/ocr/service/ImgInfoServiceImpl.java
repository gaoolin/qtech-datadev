package com.qtech.ocr.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.ocr.utils.Utils;
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

    public String cephGrwSvc(String url, JSONObject byteJson) {

        String jsonStr = Utils.connectPost(url, byteJson);

        System.out.println(jsonStr);

        return jsonStr;
    }
}
