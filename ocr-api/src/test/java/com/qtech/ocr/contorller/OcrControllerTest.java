package com.qtech.ocr.contorller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.common.utils.ImageUtils;
import com.qtech.common.utils.Utils;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/03 11:49:51
 * desc   :  TODO
 */

@SpringBootTest
@RunWith(SpringRunner.class)
class OcrControllerTest {

    File file = new File("C:\\Users\\zhilin.gao\\Desktop\\IMG_20230809_093108.jpg");
    byte[] bytes = ImageUtils.fileToByte(file);

    @Autowired
    OcrController ocrController;

    @Test
    void getOcrInfo() {

        String s = Base64.encodeBase64String(bytes);

        System.out.println(Arrays.toString(bytes));
        System.out.println(">>>>>>>>");
//        System.out.println(s);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("bucketName", "qtech-20230717");
        paramMap.put("fileName", "test8.jpg");
        paramMap.put("contents", s);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(paramMap));

        String ocrInfo = ocrController.getOcrInfo(jsonObject);

        System.out.println(ocrInfo);
    }
}
