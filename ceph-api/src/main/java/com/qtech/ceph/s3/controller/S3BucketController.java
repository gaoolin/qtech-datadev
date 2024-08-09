package com.qtech.ceph.s3.controller;

import com.qtech.ceph.common.ApiResponse;
import com.qtech.ceph.s3.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/09 13:50:50
 * desc   :
 */

@RestController
@RequestMapping("/s3/buckets")
public class S3BucketController {

    @Qualifier("bucketServiceImpl")
    @Autowired
    private BucketService bucketService;

    /**
     * 创建一个新的 S3 存储桶。
     *
     * @param bucketName 存储桶的名称
     * @return 包含操作状态的 ApiResponse
     */
    @PostMapping("/create")
    public ApiResponse<String> createBucket(@RequestParam String bucketName) {
        try {
            bucketService.createBucket(bucketName);
            return ApiResponse.success("Bucket created successfully");
        } catch (Exception e) {
            return ApiResponse.internalServerError("Bucket creation failed: " + e.getMessage());
        }
    }

    /**
     * 删除一个现有的 S3 存储桶。
     *
     * @param bucketName 要删除的存储桶名称
     * @return 包含操作状态的 ApiResponse
     */
    @DeleteMapping("/delete")
    public ApiResponse<String> deleteBucket(@RequestParam String bucketName) {
        try {
            bucketService.deleteBucket(bucketName);
            return ApiResponse.success("Bucket deleted successfully");
        } catch (Exception e) {
            return ApiResponse.internalServerError("Bucket deletion failed: " + e.getMessage());
        }
    }

    /**
     * 列出所有的 S3 存储桶。
     *
     * @return 包含所有存储桶列表的 ApiResponse
     */
    @GetMapping("/list")
    public ApiResponse<List<String>> listBuckets() {
        try {
            List<String> buckets = bucketService.listBuckets();
            return ApiResponse.success(buckets);
        } catch (Exception e) {
            return ApiResponse.internalServerError("Failed to list buckets: " + e.getMessage());
        }
    }

    // 其他可能的存储桶操作（如获取存储桶信息）
}

