package com.qtech.ceph.file;

import com.qtech.ceph.object.service.CephGrwServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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