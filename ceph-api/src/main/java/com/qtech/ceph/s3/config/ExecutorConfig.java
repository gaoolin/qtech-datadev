package com.qtech.ceph.s3.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/08 17:19:52
 * desc   :  线程池配置
 */

@Configuration
public class ExecutorConfig {

    @Bean(name = "taskExecutorService")
    public ExecutorService taskExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5, // core pool size
                10, // max pool size
                60, // keep alive time
                TimeUnit.SECONDS, // time unit
                new LinkedBlockingQueue<>(25), // queue capacity
                new ThreadFactoryBuilder().setNameFormat("Async-%d").build()
        );
        return executor;
    }
}
