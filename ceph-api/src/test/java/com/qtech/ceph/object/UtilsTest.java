package com.qtech.ceph.object;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.ceph.object.service.CephGrwServiceImpl;
import com.qtech.ceph.object.utils.Utils;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/21 14:09:32
 * desc   :
 */

@SpringBootTest()
@RunWith(SpringRunner.class)
class UtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(UtilsTest.class);

    @Autowired
    CephGrwServiceImpl cephGrwService;

    File file = new File("C:\\Users\\zhilin.gao\\Desktop\\test.jpg");
    byte[] bytes = Utils.fileToByte(file);

    @Test
    void fileToByte() {
        cephGrwService.uploadByte("qtech-20230717", "test1.jpg", bytes);
    }

    @Test
    @Scheduled(cron = "0/30 * * * * ?")
    public void getShieldDate() {
        String shieldDataUrl = "http://10.170.6.40:31555//cephgrw/api/uploadByte";
        //假设这是入参
        String str = "/123/456";
        //调用工具类
        String jsonStr = Utils.connectGet(shieldDataUrl + str);
        //接收返回数据
        Map map = JSON.parseObject(jsonStr);
//        logger.info("数据发送时间->{}", map.get("executeTime"));
        //把发送的json格式字符串转为对象形式
//        User entity = JSON.parseObject(map.get("data").toString(), User.class);
//        System.err.println(entity);
    }

    @Test
    @Scheduled(cron = "0,5 * * * * ?")
    public void postShieldData() {
        String DataUrl = "http://10.170.6.40:31555//cephgrw/api/uploadByte";

        String s = Base64.encodeBase64String(bytes);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("bucketName", "qtech-20230717");
        paramMap.put("fileName", "test8.jpg");
        paramMap.put("contents", s);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(paramMap));

        String jsonStr = Utils.connectPost(DataUrl, jsonObject);

        System.out.println(jsonStr);
    }
}
