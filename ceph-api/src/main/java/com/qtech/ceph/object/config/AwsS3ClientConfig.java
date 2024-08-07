package com.qtech.ceph.object.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/17 10:54:23
 * desc   :  Configuration class for AWS S3 client.
 */
/**
 * 配置 AWS S3 客户端和签名客户端的配置类。
 */
@Configuration
public class AwsS3ClientConfig {

    @Value("${ceph.accessKey}")
    private String accessKeyId;

    @Value("${ceph.secretKey}")
    private String secretKey;

    @Value("${ceph.region}")
    private String region;

    @Value("${ceph.endpoint}")
    private String endpoint;

    /**
     * 创建 S3AsyncClient bean。
     *
     * @return 配置好的 S3AsyncClient 实例
     */
    @Bean
    public S3AsyncClient s3AsyncClient() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKey);

        return S3AsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create(endpoint))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build();
    }

    /**
     * 创建 S3Presigner bean。
     *
     * @return 配置好的 S3Presigner 实例
     */
    @Bean
    public S3Presigner s3Presigner() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKey);

        return S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create(endpoint))
                .build();
    }
}