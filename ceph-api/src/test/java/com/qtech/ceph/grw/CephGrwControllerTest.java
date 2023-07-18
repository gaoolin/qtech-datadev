package com.qtech.ceph.grw;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/17 16:59:23
 * desc   :  TODO
 */

@SpringBootTest
@RunWith(SpringRunner.class)
class CephGrwControllerTest {

    @Autowired
    private CephGrwServiceImpl cephGrwService;

    @Test
    void readStreamObject() {
        InputStream inputStream = cephGrwService.readStreamObject("qtech-20230717", "test.jpg");
        File file = new File("output.jpg");
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
