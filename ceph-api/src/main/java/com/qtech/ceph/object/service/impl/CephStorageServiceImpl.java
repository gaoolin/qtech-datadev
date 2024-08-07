package com.qtech.ceph.object.service.impl;

import com.qtech.ceph.object.service.CephStorageService;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * Service class for Ceph object storage operations.
 * Author: Gaozhilin
 * Email: gaoolin@gmail.com
 * Date: 2023/07/14
 */

/**
 * 实现 Ceph 对象存储服务接口的服务类。
 * 提供桶的列出、创建、删除、对象的上传、下载等功能。
 */
@Service
public class CephStorageServiceImpl implements CephStorageService {

    private final S3AsyncClient s3Client;
    private final S3Presigner presigner;

    /**
     * 构造函数注入 S3AsyncClient 和 S3Presigner。
     *
     * @param s3Client  用于异步操作的 S3 客户端
     * @param presigner 用于生成预签名 URL 的 S3 签名客户端
     */
    public CephStorageServiceImpl(S3AsyncClient s3Client, S3Presigner presigner) {
        this.s3Client = s3Client;
        this.presigner = presigner;
    }

    /**
     * 列出所有桶。
     *
     * @return 包含所有桶的列表
     */
    @Override
    public List<Bucket> listAllBuckets() {
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        CompletableFuture<ListBucketsResponse> listBucketsResponse = s3Client.listBuckets(listBucketsRequest);
        return listBucketsResponse.join().buckets();
    }

    /**
     * 创建桶并列出其对象。
     *
     * @param bucketName 桶名称
     * @return 包含桶对象的列表响应
     */
    @Override
    public ListObjectsV2Response createBucketAndListObjects(String bucketName) {
        createBucket(bucketName);
        return listObjects(bucketName);
    }

    /**
     * 列出桶中的对象。
     *
     * @param bucketName 桶名称
     * @return 包含桶对象的列表响应
     */
    @Override
    public ListObjectsV2Response listObjects(String bucketName) {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder().bucket(bucketName).build();
        CompletableFuture<ListObjectsV2Response> listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
        return listObjectsResponse.join();
    }

    /**
     * 创建桶。
     *
     * @param bucketName 桶名称
     * @return 创建的桶名称
     */
    @Override
    public String createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder().bucket(bucketName).build();
        s3Client.createBucket(createBucketRequest).join();
        return bucketName;
    }

    /**
     * 删除桶。
     *
     * @param bucketName 桶名称
     */
    @Override
    public void deleteBucket(String bucketName) {
        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
        s3Client.deleteBucket(deleteBucketRequest).join();
    }

    /**
     * 上传字符串作为对象。
     *
     * @param bucketName 桶名称
     * @param objName    对象名称
     * @param content    字符串内容
     */
    @Override
    public void uploadStringAsObj(String bucketName, String objName, String content) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(objName).build();
        s3Client.putObject(putObjectRequest, AsyncRequestBody.fromString(content)).join();
    }

    /**
     * 设置对象为公共可读。
     *
     * @param bucketName 桶名称
     * @param objName    对象名称
     */
    @Override
    public void setObjPublic(String bucketName, String objName) {
        PutObjectAclRequest aclRequest = PutObjectAclRequest.builder()
                .bucket(bucketName)
                .key(objName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        s3Client.putObjectAcl(aclRequest).join();
    }

    /**
     * 下载对象到指定目录。
     *
     * @param bucketName 桶名称
     * @param keyName    对象键名
     * @param dirName    目录名
     */
    @Override
    public void downloadObj(String bucketName, String keyName, String dirName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();
        File file = new File(dirName + "/" + keyName);
        s3Client.getObject(getObjectRequest, AsyncResponseTransformer.toFile(file)).join();
    }

    /**
     * 删除对象。
     *
     * @param bucketName 桶名称
     * @param objName    对象名称
     */
    @Override
    public void deleteObj(String bucketName, String objName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(objName).build();
        s3Client.deleteObject(deleteObjectRequest).join();
    }

    /**
     * 生成预签名URL。
     *
     * @param bucketName 桶名称
     * @param keyName    对象键名
     * @return 预签名URL
     */
    @Override
    public URL generatePresignedUrl(String bucketName, String keyName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest)
                .build();
        return presigner.presignGetObject(getObjectPresignRequest).url();
    }

    /**
     * 上传对象并获取URL。
     *
     * @param bucketName 桶名称
     * @param obj        文件对象
     * @param keyName    对象键名
     * @return 预签名URL
     */
    @Override
    public URL uploadObjAndGetUrl(String bucketName, File obj, String keyName) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName).build();
        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(putObjectRequest)
                .build();
        URL presignedUrl = presigner.presignPutObject(putObjectPresignRequest).url();
        s3Client.putObject(putObjectRequest, AsyncRequestBody.fromFile(obj.toPath())).join();
        return presignedUrl;
    }

    /**
     * 上传 InputStream 作为对象。
     *
     * @param bucketName 桶名称
     * @param objName    对象名称
     * @param input      InputStream
     */
    @Override
    public void uploadInputStreamAsObj(String bucketName, String objName, InputStream input) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objName)
                .build();

        AsyncRequestBody asyncRequestBody = AsyncRequestBody.fromInputStream(input, -1L, Executors.newSingleThreadExecutor());

        s3Client.putObject(putObjectRequest, asyncRequestBody)
                .join();
    }

    /**
     * 上传字节数组作为对象。
     *
     * @param bucketName 桶名称
     * @param objName    对象名称
     * @param contents   字节数组
     */
    @Override
    public void uploadByteArrayAsObj(String bucketName, String objName, byte[] contents) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(objName).build();
        s3Client.putObject(putObjectRequest, AsyncRequestBody.fromBytes(contents)).join();
    }

    /**
     * 作为 InputStream 下载对象。
     *
     * @param bucketName 桶名称
     * @param objName    对象名称
     * @return InputStream
     */
    @Override
    public InputStream downloadObjAsInputStream(String bucketName, String objName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(objName).build();
        return s3Client.getObject(getObjectRequest, AsyncResponseTransformer.toBytes()).join().asInputStream();
    }

    /**
     * 下载对象作为字节数组。
     *
     * @param bucketName 桶名称
     * @param objName    对象名称
     * @return 字节数组
     */
    @Override
    public byte[] downloadObjAsByteArray(String bucketName, String objName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(objName).build();
        return s3Client.getObject(getObjectRequest, AsyncResponseTransformer.toBytes()).join().asByteArray();
    }
}