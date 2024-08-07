package com.qtech.ceph.rules;

import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketLifecycleConfiguration;
import software.amazon.awssdk.services.s3.model.LifecycleRule;
import software.amazon.awssdk.services.s3.model.PutBucketLifecycleConfigurationRequest;
import software.amazon.awssdk.services.s3.model.GetBucketLifecycleConfigurationRequest;
import software.amazon.awssdk.services.s3.model.GetBucketLifecycleConfigurationResponse;
import software.amazon.awssdk.regions.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/01 10:36:20
 * desc   :  设置文件对象过期时间
 */
public class SetLifecycleConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SetLifecycleConfiguration.class);

    static Region clientRegion = Region.US_EAST_1; // Update to your region
    static String bucketName = "qtech-20230717";

    // Add the rules to a new BucketLifecycleConfiguration.
    static LifecycleRule rule = LifecycleRule.builder()
            .id("rule3") // Update rule id
            .prefix("") // Update prefix if needed
            .status("Enabled")
            .transitions(t -> t
                    .storageClass("GLACIER") // Example transition storage class
                    .days(30) // Example days for transition
            )
            .build();

    public static void main(String[] args) {
        try (S3Client s3Client = S3Client.builder()
                .region(clientRegion)
                .build()) {

            // Create a BucketLifecycleConfiguration with the rules
            BucketLifecycleConfiguration lifecycleConfiguration = BucketLifecycleConfiguration.builder()
                    .rules(Collections.singletonList(rule))
                    .build();

            // Save the configuration
            s3Client.putBucketLifecycleConfiguration(PutBucketLifecycleConfigurationRequest.builder()
                    .bucket(bucketName)
                    .lifecycleConfiguration(lifecycleConfiguration)
                    .build());

            // Retrieve the configuration
            GetBucketLifecycleConfigurationResponse response = s3Client.getBucketLifecycleConfiguration(GetBucketLifecycleConfigurationRequest.builder()
                    .bucket(bucketName)
                    .build());

            // Print the retrieved configuration
            logger.info("Bucket Lifecycle Configuration: " + response.rules().toString());

        } catch (SdkException e) {
            // Catch SDK specific exceptions
            logger.error("Error: " + e.getMessage());
        }
    }
}
