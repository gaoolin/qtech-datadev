package com.qtech.service.controller.ocr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.common.utils.ImageUtils;
import com.qtech.service.utils.http.HttpUtils;
import com.qtech.service.utils.response.ApiResponse;
import com.qtech.service.utils.response.R;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.HashMap;

import static com.qtech.service.common.Constants.EQ_REVERSE_IGNORE_SIM_PREFIX;
import static org.junit.Assert.assertEquals;

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

    @Autowired
    private RedisTemplate<String, Boolean> redisTemplate;

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


    @Test
    public void testSerialization() throws Exception {
        R response = new R();
        response.setCode(200);
        response.setMsg("Success");
        response.setData("Some data");

        // 序列化对象为JSON字符串
        String jsonResult = objectMapper.writeValueAsString(response);

        // 预期的JSON字符串
        String expectedJson = "{\"Code\":200,\"Msg\":\"Success\",\"data\":\"Some data\"}";

        // 验证序列化结果是否与预期相符
        assertEquals(expectedJson, jsonResult);
    }

    @Test
    public void testDeserialization() throws Exception {
        String jsonInput = "{\"Code\":200,\"Msg\":\"Success\",\"data\":\"Some data\"}";

        // 将JSON字符串反序列化为对象
        R response = objectMapper.readValue(jsonInput, R.class);

        // 验证反序列化结果
        assertEquals(200, response.getCode());
        assertEquals("Success", response.getMsg());
        assertEquals("Some data", response.getData());
    }


    @Test
    public void testDeserialization2() throws Exception {
        redisTemplate.opsForValue().set(EQ_REVERSE_IGNORE_SIM_PREFIX + "861394058751681", true);

    }
}