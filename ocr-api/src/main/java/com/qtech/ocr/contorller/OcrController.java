package com.qtech.ocr.contorller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.ocr.service.ImgInfoServiceImpl;
import com.qtech.ocr.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/31 17:00:23
 * desc   :  TODO
 */

@RestController
public class OcrController extends BaseController {

    @Autowired
    ImgInfoServiceImpl imgInfoService;

    public String getOcrInfo(JSONObject byteJson) {

        String flag = imgInfoService.cephGrwSvc("http://10.170.6.40:31555//cephgrw/api/uploadByte", byteJson);

        if ("0".equals(flag)) {
            HashMap<String, String> map = new HashMap<>();
            map.put("file_name", byteJson.getString("fileName"));
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
            String s = Utils.connectPost("http://10.170.6.40:30113/ocrAPI", jsonObject);
            return s;
        } else {
            return null;
        }
    }
}
