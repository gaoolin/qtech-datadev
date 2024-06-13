package com.qtech.olp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@MapperScan(basePackages = "com.qtech.olp.mapper")
@SpringBootApplication
public class WbOlpChkApi
{
    public static void main(String[] args) {
        SpringApplication.run(WbOlpChkApi.class, args);
    }
}
