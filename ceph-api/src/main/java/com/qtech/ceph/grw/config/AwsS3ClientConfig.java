package com.qtech.ceph.grw.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/17 10:54:23
 * desc   :  AWS S3 客户端配置
 */

@Configuration
public class AwsS3ClientConfig {

    @Value("${ceph.accessKey}")
    private String access;
    @Value("${ceph.secretKey}")
    private String secret;
    @Value("${ceph.endpoint}")
    private String endpoint;

    @Bean
    public AmazonS3 AwsS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(access, secret);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        AmazonS3 conn = new AmazonS3Client(awsCredentials, clientConfig);
        conn.setEndpoint(endpoint);

        return conn;
    }
}
