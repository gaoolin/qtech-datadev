package com.qtech.ocr.service;

import com.alibaba.fastjson.JSONObject;
import com.qtech.ocr.utils.Utils;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/31 17:04:14
 * desc   :  服务层
 */

@Service
public class ImgInfoServiceImpl {

    public String cephGrwSvc(String url, JSONObject byteJson) {

        String jsonStr = Utils.connectPost(url, byteJson);

        return jsonStr;
    }
}
