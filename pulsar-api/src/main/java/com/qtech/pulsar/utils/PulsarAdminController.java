package com.qtech.pulsar.utils;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/14 08:25:30
 * desc   :  PulsarAdmin工具
 */


//@RestController
//@RequestMapping(value = "/pulsarAdmin/api")
public class PulsarAdminController {

    @RequestMapping(value = "/admin")
    public String create() {
        try {
            PulsarAdmin build = PulsarAdmin.builder().build();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
