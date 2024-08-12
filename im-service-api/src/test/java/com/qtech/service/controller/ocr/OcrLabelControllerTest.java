package com.qtech.service.controller.ocr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.common.utils.ImageUtils;
import com.qtech.service.utils.http.HttpUtils;
import com.qtech.service.utils.response.ApiResponse;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.HashMap;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/11 08:21:04
 * desc   :
 */


@SpringBootTest
@RunWith(SpringRunner.class)
class OcrLabelControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper(); // 使用单例模式
    File file = new File("C:\\Users\\zhilin.gao\\Desktop\\BFE1905D-EC64-43f9-B8B7-025A1D966665.png");
    byte[] bytes = ImageUtils.fileToByte(file);

    @Autowired
    OcrLabelController ocrLabelController;

    @Test
    void getOcrInfo() throws JsonProcessingException {

        String s = Base64.encodeBase64String(bytes);

        System.out.println(">>>>>>>>");

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("bucketName", "qtech-20230717");
        paramMap.put("fileName", "test34.png");
        paramMap.put("contents", s);

        ApiResponse<String> ocrInfo = ocrLabelController.getOcrResult(paramMap);

        System.out.println(ocrInfo);
    }

    @Test
    void getOcrInfo2() throws JsonProcessingException {

        String s = Base64.encodeBase64String(bytes);
        System.out.println(">>>>>>>>");
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("bucketName", "qtech-20230717");
        paramMap.put("fileName", "test34.png");
        paramMap.put("contents", s);
        System.out.println(objectMapper.writeValueAsString(paramMap));
        HttpUtils.post("http://10.170.6.40:30883/im/ocr/label", objectMapper.writeValueAsString(paramMap));
    }
}