package com.qtech.ceph.object.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/17 10:54:23
 * desc   :  Configuration class for AWS S3 client.
 */

/*@Configuration
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
}*/

@Configuration
public class AwsS3ClientConfig {

    private final String accessKey;
    private final String secretKey;
    private final String endpoint;

    /*将字段注入改为构造函数注入，提升了代码的可测试性。*/
    @Autowired
    public AwsS3ClientConfig(@Value("${ceph.accessKey}") String accessKey,
                             @Value("${ceph.secretKey}") String secretKey,
                             @Value("${ceph.endpoint}") String endpoint) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.endpoint = endpoint;
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(com.amazonaws.Protocol.HTTP);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withClientConfiguration(clientConfig)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, null))
                .build();
    }
}