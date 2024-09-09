package com.qtech.ceph.hdfs.config;

import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/02 08:42:36
 * desc   :
 */

@Configuration
public class HadoopHDFSConfig {

    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(HadoopHDFSConfig.class);

    @Value("${hdfs.hdfsPath}")
    private String hdfsPath;
    @Value("${hdfs.hadoopUser}")
    private String hadoopUser;

    /**
     * hadoop hdfs 配置参数对象
     *
     * @return
     */
    @Bean
    public org.apache.hadoop.conf.Configuration getConfiguration() {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        configuration.set("fs.hdfs.impl.disable.cache", "true");
        configuration.set("fs.defaultFS", "hdfs://cluster");
        configuration.set("dfs.nameservices", "cluster");
        configuration.set("dfs.ha.namenodes.cluster", "nn1,nn2");
        configuration.set("dfs.namenode.rpc-address.cluster.nn1", "im01:8020");
        configuration.set("dfs.namenode.rpc-address.cluster.nn2", "im02:8020");
        configuration.set("dfs.client.failover.proxy.provider.cluster", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        return configuration;
    }

    /**
     * hadoop filesystem 文件系统
     *
     * @return
     */
    @Bean
    public FileSystem getFileSystem() {
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(new URI(hdfsPath), getConfiguration(), hadoopUser);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        }
        return fileSystem;
    }
}
