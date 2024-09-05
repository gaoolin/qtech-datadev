package com.qtech.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Hello world!
 */

@EnableAspectJAutoProxy  // 启用 AspectJ 的自动代理功能
@SpringBootApplication
public class QtechImApi {
    public static void main(String[] args) {
        SpringApplication.run(QtechImApi.class, args);
    }
}
