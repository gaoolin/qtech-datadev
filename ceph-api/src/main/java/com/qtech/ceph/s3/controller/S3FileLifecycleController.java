package com.qtech.ceph.s3.controller;

import com.qtech.ceph.common.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.Collections;
import java.util.List;

import static com.qtech.ceph.s3.utils.S3Constants.EXPIRATION_DAYS;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/09 13:52:23
 * desc   :
 */

/**
 * 控制器类，用于管理文件的生命周期
 */
@RestController
@RequestMapping("/s3/lifecycle")
public class S3FileLifecycleController {
    private static final Logger logger = LoggerFactory.getLogger(S3FileLifecycleController.class);

    @Autowired
    private S3Client s3Client;

    /**
     * 设置文件的生命周期规则
     *
     * @param bucketName 存储桶名称
     * @param ruleId     规则ID
     * @param prefix     文件前缀
     * @return ApiResponse 操作状态
     */
    @PostMapping("/set")
    public ApiResponse<String> setFileLifecycle(@RequestParam String bucketName,
                                                @RequestParam String ruleId,
                                                @RequestParam String prefix) {
        try {
            // 创建生命周期规则
            LifecycleRule rule = LifecycleRule.builder()
                    .id(ruleId)
                    .prefix(prefix)
                    .status("Enabled")
                    .transitions(t -> t
                            .storageClass("GLACIER")
                            .days(EXPIRATION_DAYS)
                    )
                    .build();

            // 创建生命周期配置
            BucketLifecycleConfiguration lifecycleConfiguration = BucketLifecycleConfiguration.builder()
                    .rules(Collections.singletonList(rule))
                    .build();

            // 设置存储桶的生命周期配置
            s3Client.putBucketLifecycleConfiguration(PutBucketLifecycleConfigurationRequest.builder()
                    .bucket(bucketName)
                    .lifecycleConfiguration(lifecycleConfiguration)
                    .build());

            return ApiResponse.success("File lifecycle configuration set successfully");
        } catch (SdkException e) {
            logger.error("Error setting file lifecycle: " + e.getMessage());
            return ApiResponse.internalServerError("Failed to set file lifecycle: " + e.getMessage());
        }
    }

    /**
     * 获取文件的生命周期规则
     *
     * @param bucketName 存储桶名称
     * @return ApiResponse 包含生命周期规则的响应
     */
    @GetMapping("/fetch")
    public ApiResponse<List<LifecycleRule>> getFileLifecycle(@RequestParam String bucketName) {
        try {
            // 获取存储桶的生命周期配置
            GetBucketLifecycleConfigurationResponse response = s3Client.getBucketLifecycleConfiguration(GetBucketLifecycleConfigurationRequest.builder()
                    .bucket(bucketName)
                    .build());

            // 返回配置
            return ApiResponse.success(response.rules());
        } catch (SdkException e) {
            logger.error("Error retrieving file lifecycle: " + e.getMessage());
            return ApiResponse.internalServerError("Failed to retrieve file lifecycle: " + e.getMessage());
        }
    }
}