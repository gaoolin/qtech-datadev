package com.qtech.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */

@MapperScan(basePackages = "com.qtech.service.mapper.*")
@SpringBootApplication
public class QtechImApi
{
    public static void main(String[] args) {
        SpringApplication.run(QtechImApi.class, args);
    }
}