package com.qtech.ceph.s3.service.impl;

import com.qtech.ceph.exception.StorageException;
import com.qtech.ceph.s3.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/09 11:44:14
 * desc   :
 */

@Service
public class BucketServiceImpl implements BucketService {

    private final S3Client s3Client;

    /**
     * 构造方法注入 S3Client。
     *
     * @param s3Client S3 同步客户端，用于与 S3 服务进行交互。
     */
    @Autowired
    public BucketServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * 创建存储桶
     *
     * 使用 S3 同步客户端创建一个新的存储桶。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。必须唯一。
     * @throws StorageException 如果创建存储桶过程中出现任何错误。
     */
    @Override
    public void createBucket(String bucketName) throws StorageException {
        try {
            s3Client.createBucket(CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            throw new StorageException("Failed to create bucket: " + bucketName, e);
        }
    }

    /**
     * 列出所有存储桶
     *
     * 使用 S3 同步客户端获取所有存储桶的列表。如果操作失败，将抛出 StorageException。
     *
     * @return 存储桶名称列表。返回所有现有存储桶的名称。
     * @throws StorageException 如果获取存储桶列表过程中出现任何错误。
     */
    @Override
    public List<String> listBuckets() throws StorageException {
        try {
            ListBucketsResponse response = s3Client.listBuckets();
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
     * 使用 S3 同步客户端删除指定名称的存储桶。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。存储桶必须为空才能被删除。
     * @throws StorageException 如果删除存储桶过程中出现任何错误。
     */
    @Override
    public void deleteBucket(String bucketName) throws StorageException {
        try {
            s3Client.deleteBucket(DeleteBucketRequest.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            throw new StorageException("Failed to delete bucket: " + bucketName, e);
        }
    }
}