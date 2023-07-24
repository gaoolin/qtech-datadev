package com.qtech.ceph.grw;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/21 14:09:32
 * desc   :  TODO
 */

@SpringBootTest()
@RunWith(SpringRunner.class)
class UtilsTest {

    @Autowired
    CephGrwServiceImpl cephGrwService;

    @Test
    void fileToByte() {
        File file = new File("C:\\Users\\zhilin.gao\\Desktop\\test.jpg");
        byte[] bytes = Utils.fileToByte(file);
        cephGrwService.uploadByte("qtech-20230717", "test1.jpg", bytes);
    }

//    @Test
//    @Scheduled(cron = "0/30 * * * * ?")
//    void rpcToByteTest() {
//        //创建HttpClient对象
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpGet httpGet = new HttpGet(url);
//    }
}
