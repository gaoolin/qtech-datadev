package com.qtech.ceph.s3.service.impl;

import com.qtech.ceph.exception.StorageException;
import com.qtech.ceph.s3.service.FileService;
import com.qtech.ceph.s3.utils.S3Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.BytesWrapper;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/08 16:46:51
 * desc   :
 */

/**
 * 实现文件管理接口
 * <p>
 * 负责处理与 S3 文件相关的操作，包括上传、下载、删除文件以及获取文件元数据。
 */
@Service
public class FileServiceSyncImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceSyncImpl.class);
    private final S3AsyncClient s3AsyncClient;
    private final S3Presigner s3Presigner;
    private final ExecutorService taskExecutorService;

    /**
     * 构造方法注入 S3AsyncClient 和 S3Presigner。
     *
     * @param s3AsyncClient S3 异步客户端，用于与 S3 服务进行交互。
     * @param s3Presigner   S3 预签名生成器，用于生成预签名 URL。
     */
    @Autowired
    public FileServiceSyncImpl(S3AsyncClient s3AsyncClient, S3Presigner s3Presigner, @Qualifier("taskExecutorService") ExecutorService taskExecutorService) {
        this.s3AsyncClient = s3AsyncClient;
        this.s3Presigner = s3Presigner;
        this.taskExecutorService = taskExecutorService;
    }

    /**
     * 表单上传文件
     * <p>
     * 将文件上传到指定存储桶。上传文件的输入流将被传送到 S3。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。文件将被上传到该存储桶。
     * @param fileName   文件名称。上传的文件将使用该名称存储。
     * @param file       文件输入流。文件内容。
     * @throws StorageException 如果上传文件过程中出现任何错误。
     */
    @Async("taskExecutor")
    @Override
    public void uploadFile(String bucketName, String fileName, InputStream file) throws IOException {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            CompletableFuture<PutObjectResponse> future = s3AsyncClient.putObject(
                    putObjectRequest,
                    AsyncRequestBody.fromInputStream(file, (long) file.available(), taskExecutorService) // 使用正确的构造函数
            );

            future.join(); // 等待上传完成
        } catch (Exception e) {
            throw new StorageException("Failed to upload file: " + fileName + " to bucket: " + bucketName, e);
        }
    }

    /**
     * Base64编码的字节数组上传文件
     * <p>
     * 将 Base64 编码的字节数组上传到指定存储桶。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。文件将被上传到该存储桶。
     * @param fileName   文件名称。上传的文件将使用该名称存储。
     * @param bytes      文件字节数组。文件内容。
     * @throws StorageException 如果上传文件过程中出现任何错误。
     */
    @Override
    public void uploadByteArrayAsObj(String bucketName, String fileName, byte[] bytes) throws StorageException {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            CompletableFuture<PutObjectResponse> future = s3AsyncClient.putObject(
                    putObjectRequest,
                    AsyncRequestBody.fromBytes(bytes)
            );
            future.join(); // 等待上传完成
        } catch (Exception e) {
            throw new StorageException("Failed to upload byte array as object: " + fileName + " to bucket: " + bucketName, e);
        }
    }

    /**
     * 流式上传文件
     * <p>
     * 将文件输入流上传到指定存储桶。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。文件将被上传到该存储桶。
     * @param fileName   文件名称。上传的文件将使用该名称存储。
     * @param fileStream 文件输入流。文件内容。
     * @throws StorageException 如果上传文件过程中出现任何错误。
     */
    @Override
    public void uploadFileStream(String bucketName, String fileName, InputStream fileStream) throws StorageException {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            CompletableFuture<PutObjectResponse> future = s3AsyncClient.putObject(
                    putObjectRequest,
                    AsyncRequestBody.fromInputStream(fileStream, (long) fileStream.available(), taskExecutorService)
            );
            future.join(); // 等待上传完成
        } catch (Exception e) {
            throw new StorageException("Failed to upload file stream: " + fileName + " to bucket: " + bucketName, e);
        }
    }

    /**
     * 分块上传文件
     * <p>
     * 将文件分块上传到指定存储桶。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName  存储桶名称。文件将被上传到该存储桶。
     * @param fileName    文件名称。上传的文件将使用该名称存储。
     * @param fileStream  文件输入流。文件内容。
     * @param chunkIndex  当前块的索引，从 0 开始。
     * @param totalChunks 总块数。
     * @throws StorageException 如果上传文件过程中出现任何错误。
     */
    @Override
    public void uploadFileInChunks(String bucketName, String fileName, InputStream fileStream, int chunkIndex, int totalChunks) throws StorageException {
        // 这里假设已实现分块上传逻辑
        try {
            // 分块上传逻辑需要实现
        } catch (Exception e) {
            throw new StorageException("Failed to upload file in chunks: " + fileName + " to bucket: " + bucketName, e);
        }
    }

    /**
     * 下载文件到服务器临时路径，然后返回文件输入流。
     * <p>
     * 从指定存储桶中下载文件。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。文件将从该存储桶中下载。
     * @param fileName   文件名称。要下载的文件名称。
     * @return 文件输入流。下载的文件内容。
     * @throws StorageException 如果下载文件过程中出现任何错误。
     */
    @Override
    public InputStream downloadFileAsStream(String bucketName, String fileName) throws StorageException {
        Path tempFilePath = Paths.get("/home/qtech/" + fileName);

        try {
            // 下载文件到指定路径
            CompletableFuture<GetObjectResponse> future = s3AsyncClient.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    AsyncResponseTransformer.toFile(tempFilePath)
            );

            // 等待下载完成
            future.join(); // 等待操作完成

            // 检查文件大小
            long fileSize = Files.size(tempFilePath);
            if (fileSize > 2 * 1024 * 1024 * 1024L) { // 2GB
                throw new StorageException("File is too large to handle: " + fileName);
            }

            // 返回文件输入流
            return Files.newInputStream(tempFilePath.toFile().toPath());
        } catch (IOException e) {
            throw new StorageException("Failed to handle file: " + fileName, e);
        } catch (Exception e) {
            throw new StorageException("Failed to download file: " + fileName + " from bucket: " + bucketName, e);
        } finally {
            // 清理临时文件
            try {
                Files.deleteIfExists(tempFilePath);
            } catch (IOException e) {
                logger.error("Failed to delete temporary file: " + tempFilePath, e);
            }
        }
    }

    /**
     * 流式下载文件
     * <p>
     * 从指定存储桶中流式下载文件。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。文件将从该存储桶中下载。
     * @param fileName   文件名称。要下载的文件名称。
     * @return 文件输入流。下载的文件内容。
     * @throws StorageException 如果下载文件过程中出现任何错误。
     */
    @Override
    public byte[] downloadFileAsBytes(String bucketName, String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        CompletableFuture<byte[]> future = s3AsyncClient.getObject(getObjectRequest, AsyncResponseTransformer.toBytes())
                .thenApply(BytesWrapper::asByteArray)
                .exceptionally(ex -> {
                    // Handle the exception
                    throw new RuntimeException("Failed to download file as bytes", ex);
                });

        try {
            // Blocking call to wait for the asynchronous operation to complete
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to download file as bytes", e);
        }
    }

    /**
     * 生成预签名URL
     * <p>
     * 为指定文件生成一个有效的预签名 URL。该 URL 允许临时访问文件。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。文件所在的存储桶。
     * @param fileName   文件名称。要生成预签名 URL 的文件名称。
     * @return 预签名 URL。允许访问文件的 URL。
     * @throws StorageException 如果生成预签名 URL 过程中出现任何错误。
     */
    @Override
    public URL generatePresignedUrl(String bucketName, String fileName) throws StorageException {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            // 创建预签名请求
            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(S3Constants.DEFAULT_SIGNATURE_DURATION)
                    .getObjectRequest(getObjectRequest)
                    .build();

            // 生成预签名 URL
            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);

            return presignedGetObjectRequest.url();
        } catch (Exception e) {
            throw new StorageException("Failed to generate presigned URL for file: " + fileName + " in bucket: " + bucketName, e);
        }
    }

    @Override
    public InputStream downloadFileInChunks(String bucketName, String fileName, int chunkIndex, int chunkSize) throws
            StorageException {
         // 实现分块下载文件的逻辑
        return null;
    }

    /**
     * 删除文件
     * <p>
     * 从指定存储桶中删除文件。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。文件将从该存储桶中删除。
     * @param fileName   文件名称。要删除的文件名称。
     * @throws StorageException 如果删除文件过程中出现任何错误。
     */
    @Override
    public void deleteFile(String bucketName, String fileName) throws StorageException {
        try {
            CompletableFuture<Void> future = s3AsyncClient.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build()
            ).thenApply(response -> null);
            future.join(); // 等待删除完成
        } catch (Exception e) {
            throw new StorageException("Failed to delete file: " + fileName + " from bucket: " + bucketName, e);
        }
    }

    @Override
    public Map<String, String> getFileMetadata(String bucketName, String fileName) throws StorageException {
        return null;
    }

    /**
     * 列出存储桶中的文件
     * <p>
     * 列出指定存储桶中的所有文件。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。要列出文件的存储桶。
     * @return 文件名称列表。存储桶中所有文件的名称。
     * @throws StorageException 如果列出文件过程中出现任何错误。
     */
    @Override
    public List<String> listFiles(String bucketName) throws StorageException {
        try {
            CompletableFuture<ListObjectsResponse> future = s3AsyncClient.listObjects(
                    ListObjectsRequest.builder()
                            .bucket(bucketName)
                            .build()
            );
            ListObjectsResponse response = future.join(); // 等待操作完成
            return response.contents().stream()
                    .map(S3Object::key)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new StorageException("Failed to list files in bucket: " + bucketName, e);
        }
    }
}