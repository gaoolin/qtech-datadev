package com.qtech.ocr.service;

import com.alibaba.fastjson.JSONObject;
import com.qtech.common.utils.HttpConnectUtils;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/31 17:04:14
 * desc   :  服务层
 */

@Service
public class ImgInfoServiceImpl {

    public String cephObj(String url, JSONObject byteJson) {

        return HttpConnectUtils.post(url, byteJson);
    }
}
