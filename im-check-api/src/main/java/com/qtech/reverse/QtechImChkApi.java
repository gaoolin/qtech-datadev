package com.qtech.reverse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@MapperScan(basePackages = "com.qtech.reverse.mapper")
@SpringBootApplication
public class QtechImChkApi
{
    public static void main(String[] args) {
        SpringApplication.run(QtechImChkApi.class, args);
    }
}
