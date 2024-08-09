package com.qtech.ceph.s3.service.impl;

import com.qtech.ceph.exception.StorageException;
import com.qtech.ceph.s3.service.FileService;
import com.qtech.ceph.s3.utils.S3Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实现文件管理接口
 * <p>
 * 负责处理与 S3 文件相关的操作，包括上传、下载、删除文件以及获取文件元数据。
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceSyncImpl.class);
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    /**
     * 构造方法注入 S3Client 和 S3Presigner。
     *
     * @param s3Client    S3 客户端，用于与 S3 服务进行交互。
     * @param s3Presigner S3 预签名生成器，用于生成预签名 URL。
     */

    @Autowired
    public FileServiceImpl(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
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
     * @throws IOException      如果读取文件内容过程中出现任何错误。
     */
    @Override
    public void uploadFile(String bucketName, String fileName, InputStream file) throws StorageException, IOException {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(file, file.available())
            );
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

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(bytes)
            );
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

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(fileStream, fileStream.available())
            );
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
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.getObject(
                    getObjectRequest,
                    ResponseTransformer.toFile(tempFilePath)
            );

            long fileSize = Files.size(tempFilePath);
            if (fileSize > 2 * 1024 * 1024 * 1024L) { // 2GB
                throw new StorageException("File is too large to handle: " + fileName);
            }

            return Files.newInputStream(tempFilePath);
        } catch (IOException e) {
            throw new StorageException("Failed to handle file: " + fileName, e);
        } catch (Exception e) {
            throw new StorageException("Failed to download file: " + fileName + " from bucket: " + bucketName, e);
        } finally {
            try {
                Files.deleteIfExists(tempFilePath);
            } catch (IOException e) {
                logger.error("Failed to delete temporary file: " + tempFilePath, e);
            }
        }
    }

    /**
     * 从指定的 S3 存储桶中下载文件并将其内容作为字节数组返回。
     * <p>
     * 这个方法使用同步 S3 客户端从指定的存储桶中获取文件内容，然后将其读入一个字节数组中。
     * 如果下载过程中发生 IO 错误，则会抛出 {@link StorageException} 异常。
     * <p>
     * 注意：此方法不处理文件过大导致的内存问题，适合处理较小的文件。
     *
     * @param bucketName 存储桶名称。文件将从该存储桶中下载。
     * @param fileName   文件名称。要下载的文件的名称。
     * @return 文件内容的字节数组。
     * @throws StorageException 如果在下载文件过程中出现任何错误，例如文件不存在、权限问题或 I/O 错误。
     */
    @Override
    public byte[] downloadFileAsBytes(String bucketName, String fileName) throws StorageException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        try (InputStream inputStream = s3Client.getObject(getObjectRequest);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            throw new StorageException("Failed to download file as bytes", e);
        }
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
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Failed to delete file: " + fileName + " from bucket: " + bucketName, e);
        }
    }

    /**
     * 生成预签名 URL
     * <p>
     * 为指定存储桶中的文件生成一个带有有效期的预签名 URL。生成的 URL 可以用于文件下载。
     *
     * @param bucketName 存储桶名称。文件所在的存储桶。
     * @param fileName   文件名称。要生成预签名 URL 的文件名称。
     * @return 预签名 URL。
     * @throws StorageException 如果生成预签名 URL 过程中出现任何错误。
     */
    @Override
    public URL generatePresignedUrl(String bucketName, String fileName) throws StorageException {
        try {
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .getObjectRequest(GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build())
                    .signatureDuration(S3Constants.DEFAULT_SIGNATURE_DURATION)
                    .build();

            PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
            return presignedRequest.url();
        } catch (Exception e) {
            throw new StorageException("Failed to generate presigned URL for file: " + fileName + " in bucket: " + bucketName, e);
        }
    }

    @Override
    public InputStream downloadFileInChunks(String bucketName, String fileName, int chunkIndex, int chunkSize) throws StorageException {
        return null;
    }

    /**
     * 列出指定 S3 存储桶中的所有文件。
     * <p>
     * 这个方法使用同步 S3 客户端列出存储桶中的所有文件，并将它们的键（名称）作为字符串列表返回。
     * 如果列出文件过程中出现任何错误，例如存储桶不存在或权限问题，将抛出 {@link StorageException} 异常。
     *
     * @param bucketName 存储桶名称。要列出文件的存储桶名称。
     * @return 存储桶中所有文件的名称列表。
     * @throws StorageException 如果在列出文件过程中出现任何错误，例如存储桶不存在、权限问题或其他 S3 操作错误。
     */
    @Override
    public List<String> listFiles(String bucketName) throws StorageException {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        try {
            ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
            List<String> fileNames = new ArrayList<>();

            for (S3Object s3Object : listObjectsResponse.contents()) {
                fileNames.add(s3Object.key());
            }

            return fileNames;

        } catch (Exception e) {
            throw new StorageException("Failed to list files in bucket", e);
        }
    }

    /**
     * 获取文件元数据
     * <p>
     * 从指定存储桶中获取文件的元数据。如果操作失败，将抛出 StorageException。
     *
     * @param bucketName 存储桶名称。文件所在的存储桶。
     * @param fileName   文件名称。要获取元数据的文件名称。
     * @return 文件元数据。包括文件的大小、类型等信息。
     * @throws StorageException 如果获取文件元数据过程中出现任何错误。
     */
    @Override
    public Map<String, String> getFileMetadata(String bucketName, String fileName) throws StorageException {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            HeadObjectResponse response = s3Client.headObject(headObjectRequest);
            return response.metadata();
        } catch (Exception e) {
            throw new StorageException("Failed to get file metadata: " + fileName + " from bucket: " + bucketName, e);
        }
    }

    /**
     * 检查指定桶中是否存在同名文件
     *
     * @param bucketName 桶的名称
     * @param fileName   文件的名称
     * @return true 如果文件存在；false 如果文件不存在
     */
    @Override
    public boolean doesFileExist(String bucketName, String fileName) {
        try {
            // 构建 HeadObjectRequest 请求
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            // 调用 headObject 方法
            HeadObjectResponse headObjectResponse = s3Client.headObject(headObjectRequest);
            // 如果没有抛出异常，则文件存在
            return headObjectResponse.sdkHttpResponse().isSuccessful();
        } catch (NoSuchKeyException e) {
            // 如果捕获 NoSuchKeyException 异常，则文件不存在
            return false;
        } catch (S3Exception e) {
            // 处理其他可能的 S3 异常
            throw new StorageException("Failed to check if file exists: " + fileName + " in bucket: " + bucketName, e);
        }
    }
}
