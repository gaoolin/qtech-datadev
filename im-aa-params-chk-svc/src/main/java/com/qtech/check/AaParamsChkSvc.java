package com.qtech.check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 13:25:52
 * desc   :
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.qtech.check.*", "com.qtech.share.aa.constant"})
public class AaParamsChkSvc {
    public static void main(String[] args) {
        SpringApplication.run(AaParamsChkSvc.class, args);
    }
}