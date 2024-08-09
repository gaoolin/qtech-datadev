package com.qtech.ceph.s3.service.impl;

import com.qtech.ceph.exception.StorageException;
import com.qtech.ceph.s3.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/08 16:43:20
 * desc   :  详细注解说明
 * 类注解
 *
 * @Service: 标记为 Spring 服务组件，允许 Spring 扫描并将其作为 bean 管理。
 * 注释详细说明了这个服务类的功能，即处理与存储桶相关的所有操作。
 * 构造函数
 *
 * @Autowired: 指示 Spring 注入 S3AsyncClient 实例。
 * 注释说明了 S3AsyncClient 的用途，以及如何通过构造函数注入。
 * 方法
 *
 * createBucket(String bucketName)
 *
 * 注释详细说明了方法的功能、参数和异常。
 * @param: 说明参数的含义和要求。
 * @throws: 指明异常的原因和可能发生的情况。
 * listBuckets()
 *
 * 注释详细说明了方法的功能、返回值和异常。
 * @return: 说明返回值的类型和内容。
 * @throws: 指明异常的原因和可能发生的情况。
 * deleteBucket(String bucketName)
 *
 * 注释详细说明了方法的功能、参数和异常。
 * @param: 说明参数的含义和要求。
 * @throws: 指明异常的原因和可能发生的情况。
 */

/**
 * 实现存储桶管理接口
 *
 * 负责处理与 S3 存储桶相关的操作，包括创建、删除和列出存储桶。
 */
@Service
public class BucketServiceSyncImpl implements BucketService {

    private final S3AsyncClient s3AsyncClient;

    /**
     * 构造方法注入 S3AsyncClient。
     *
     * @param s3AsyncClient S3 异步客户端，用于与 S3 服务进行交互。
     */
    @Autowired
    public BucketServiceSyncImpl(S3AsyncClient s3AsyncClient) {
        this.s3AsyncClient = s3AsyncClient;
    }

    /**
     * 创建存储桶
     *
     * 使用 S3 异步客户端创建一个新的存储桶。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。必须唯一。
     * @throws StorageException 如果创建存储桶过程中出现任何错误。
     */
    @Override
    public void createBucket(String bucketName) throws StorageException {
        try {
            CompletableFuture<CreateBucketResponse> future = s3AsyncClient.createBucket(
                    CreateBucketRequest.builder()
                            .bucket(bucketName)
                            .build()
            );
            future.join(); // 等待创建完成
        } catch (Exception e) {
            throw new StorageException("Failed to create bucket: " + bucketName, e);
        }
    }

    /**
     * 列出所有存储桶
     *
     * 使用 S3 异步客户端获取所有存储桶的列表。如果操作失败，将抛出 StorageException。
     *
     * @return 存储桶名称列表。返回所有现有存储桶的名称。
     * @throws StorageException 如果获取存储桶列表过程中出现任何错误。
     */
    @Override
    public List<String> listBuckets() throws StorageException {
        try {
            CompletableFuture<ListBucketsResponse> future = s3AsyncClient.listBuckets(
                    ListBucketsRequest.builder().build()
            );
            ListBucketsResponse response = future.join(); // 等待操作完成
            return response.buckets().stream()
                    .map(Bucket::name)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new StorageException("Failed to list buckets", e);
        }
    }

    /**
     * 删除存储桶
     *
     * 使用 S3 异步客户端删除指定名称的存储桶。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。存储桶必须为空才能被删除。
     * @throws StorageException 如果删除存储桶过程中出现任何错误。
     */
    @Override
    public void deleteBucket(String bucketName) throws StorageException {
        try {
            CompletableFuture<Void> future = s3AsyncClient.deleteBucket(
                    DeleteBucketRequest.builder()
                            .bucket(bucketName)
                            .build()
            ).thenApply(response -> null);
            future.join(); // 等待删除完成
        } catch (Exception e) {
            throw new StorageException("Failed to delete bucket: " + bucketName, e);
        }
    }
}