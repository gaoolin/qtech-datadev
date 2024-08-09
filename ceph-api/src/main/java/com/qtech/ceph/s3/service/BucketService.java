package com.qtech.ceph.s3.service;

import com.qtech.ceph.exception.StorageException;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/08 15:13:17
 * desc   :  存储桶管理接口
 */


public interface BucketService {

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     * @throws StorageException 存储异常
     */
    void createBucket(String bucketName) throws StorageException;

    /**
     * 列出所有存储桶
     *
     * @return 存储桶名称列表
     * @throws StorageException 存储异常
     */
    List<String> listBuckets() throws StorageException;

    /**
     * 删除存储桶
     *
     * @param bucketName 存储桶名称
     * @throws StorageException 存储异常
     */
    void deleteBucket(String bucketName) throws StorageException;
}

