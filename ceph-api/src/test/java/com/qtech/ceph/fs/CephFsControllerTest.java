package com.qtech.ceph.fs;

import com.qtech.ceph.grw.CephGrwServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/17 16:40:54
 * desc   :  TODO
 */

@SpringBootTest()
@RunWith(SpringRunner.class)
class CephFsControllerTest {

    @Autowired
    private CephGrwServiceImpl cephGrwService;

    @Test
    void downloadFileByPath() {
        cephGrwService.downloadFile("qtech-20230717", "test.jpg", "C:\\Users\\zhilin.gao\\Desktop\\test.jpg");
    }
}
