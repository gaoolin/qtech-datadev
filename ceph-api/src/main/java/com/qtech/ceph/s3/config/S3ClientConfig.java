package com.qtech.ceph.s3.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/08 10:09:38
 * desc   :
 */

@Configuration
public class S3ClientConfig {
    private static final Logger logger = LoggerFactory.getLogger(S3ClientConfig.class);

    @Value("${aws.s3.accessKey}")
    private String accessKeyId;

    @Value("${aws.s3.secretKey}")
    private String secretAccessKey;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.endpoint}")
    private String endpoint;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Bean
    public S3Client s3Client() {
        try {
            return S3Client.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                    .region(Region.of(region))
                    .endpointOverride(new URI(endpoint))
                    .build();
        } catch (URISyntaxException e) {
            logger.error("Invalid S3 endpoint URI: " + endpoint, e);
            throw new RuntimeException("Failed to create S3Client", e);
        } catch (Exception e) {
            logger.error("Error creating S3Client", e);
            throw new RuntimeException("Failed to create S3Client", e);
        }
    }

    @Bean
    public S3AsyncClient s3AsyncClient() {
        try {
            return S3AsyncClient.builder()
                    .credentialsProvider(StaticCredentialsProvider
                            .create(AwsBasicCredentials
                                    .create(accessKeyId, secretAccessKey)))
                    .region(Region.of(region))
                    .endpointOverride(new URI(endpoint))
                    .build();
        } catch (URISyntaxException e) {
            logger.error("Invalid S3 endpoint URI: " + endpoint, e);
            throw new RuntimeException("Failed to create S3AsyncClient", e);
        } catch (Exception e) {
            logger.error("Error creating S3AsyncClient", e);
            throw new RuntimeException("Failed to create S3AsyncClient", e);
        }
    }

    @Bean
    public S3Presigner s3Presigner() {
        try {
            return S3Presigner.builder()
                    .credentialsProvider(StaticCredentialsProvider
                            .create(AwsBasicCredentials
                                    .create(accessKeyId, secretAccessKey)))
                    .region(Region.of(region))
                    .endpointOverride(new URI(endpoint))
                    .build();
        } catch (URISyntaxException e) {
            logger.error("Invalid S3 endpoint URI: " + endpoint, e);
            throw new RuntimeException("Failed to create S3Presigner", e);
        } catch (Exception e) {
            logger.error("Error creating S3Presigner", e);
            throw new RuntimeException("Failed to create S3Presigner", e);
        }
    }
}