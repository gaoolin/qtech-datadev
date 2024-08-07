package com.qtech.ocr.contorller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.common.utils.HttpConnectUtils;
import com.qtech.ocr.service.ImgInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/31 17:00:23
 * desc   :  OCR API
 */

@RestController
@RequestMapping("/ocr/api")
public class OcrController extends BaseController {

/*    @Autowired
    ImgInfoService imgInfoService;

    @ApiOperation(value = "GetInfo", notes = "GetInfo")
    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    public String getOcrInfo(@RequestBody JSONObject byteJson) {

        String flag = imgInfoService.cephObj("http://10.170.6.40:31555/ceph/obj/uploadByte", byteJson);

        if ("0".equals(flag)) {
            HashMap<String, String> map = new HashMap<>();
            map.put("file_name", byteJson.getString("fileName"));
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
            return HttpConnectUtils.post("http://10.170.6.40:30113/ocrAPI", jsonObject);
        }
        return null;
    }*/


    @Autowired
    private ImgInfoService imgInfoService;

    /**
     * 获取 OCR 信息并上传文件
     *
     * @param byteJson 请求体，包含文件信息
     * @return OCR API 返回的响应
     */
    @ApiOperation(value = "GetInfo", notes = "Get OCR info and upload file")
    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    public String getOcrInfo(@RequestBody JSONObject byteJson) {
        int code = 0;
        String msg;
        JSONObject data;
        // 从请求体中提取信息
        String fileName = byteJson.getString("fileName");
        String base64Contents = byteJson.getString("base64Contents");
        String bucketName = "qtech-20230717";
        ResponseEntity<String> mapHttpEntity = imgInfoService.cephObj(bucketName, fileName, base64Contents);
        String responseBody = mapHttpEntity.getBody();
        // 将 JSON 字符串解析为 JSONObject
        JSONObject jsonObject = JSON.parseObject(responseBody);
        if (jsonObject != null) {
            // 获取响应码
            code = jsonObject.getIntValue("code");
            // 获取响应消息
            msg = jsonObject.getString("msg");
            // 获取响应数据
            data = jsonObject.getJSONObject("data");
        }

        // 检查响应内容
        if (code == 200) {
            Map<String, String> map = new HashMap<>();
            map.put("file_name", fileName);
            return HttpConnectUtils.post("http://10.170.6.40:30113/ocrAPI", JSONObject.parseObject(JSON.toJSONString(map)));
        }
        return null;
    }
}
