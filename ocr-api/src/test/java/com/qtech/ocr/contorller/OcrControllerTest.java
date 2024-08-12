package com.qtech.ocr.contorller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.common.utils.ImageUtils;
import com.qtech.ocr.common.ApiResponse;
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
 * date   :  2023/08/03 11:49:51
 * desc   :
 */

@SpringBootTest
@RunWith(SpringRunner.class)
class OcrControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper(); // 使用单例模式
    File file = new File("C:\\Users\\zhilin.gao\\Desktop\\BFE1905D-EC64-43f9-B8B7-025A1D966665.png");
    byte[] bytes = ImageUtils.fileToByte(file);

    @Autowired
    OcrController ocrController;

    @Test
    void getOcrInfo() throws JsonProcessingException {

        String s = Base64.encodeBase64String(bytes);

        System.out.println(">>>>>>>>");

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("bucketName", "qtech-20230717");
        paramMap.put("fileName", "test34.png");
        paramMap.put("contents", s);

        ApiResponse<String> ocrInfo = ocrController.getOcrResult(paramMap);

        System.out.println(ocrInfo);
    }
}
