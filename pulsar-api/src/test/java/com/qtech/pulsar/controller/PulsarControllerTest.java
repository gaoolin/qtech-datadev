package com.qtech.pulsar.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/07 17:06:51
 * desc   :  TODO
 */

@SpringBootTest
@RunWith(SpringRunner.class)
class PulsarControllerTest {

    @Autowired
    PulsarProducerController pulsarController;

    @Test
    void sengMsg() {
    }
}
