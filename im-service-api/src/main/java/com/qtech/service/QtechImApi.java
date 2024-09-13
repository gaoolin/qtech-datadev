package com.qtech.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Hello world!
 */


@EnableAspectJAutoProxy  // 启用 AspectJ 的自动代理功能
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class) // 禁用SpringBoot自动处理错误页面
public class QtechImApi {
    public static void main(String[] args) {
        SpringApplication.run(QtechImApi.class, args);
    }
}
