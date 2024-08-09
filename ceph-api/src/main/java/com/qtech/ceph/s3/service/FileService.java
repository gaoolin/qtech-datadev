package com.qtech.ceph.s3.service;


import com.qtech.ceph.exception.StorageException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/08 15:14:11
 * desc   :  文件管理接口
 */

public interface FileService {

    /**
     * 表单上传文件
     * @param bucketName 存储桶名称
     * @param file 文件
     * @throws StorageException 存储异常
     */
    void uploadFile(String bucketName, String fileName, InputStream file) throws StorageException, IOException;

    /**
     * Base64编码的字节数组上传文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @param bytes 文件字节数组
     * @throws StorageException 存储异常
     */
    void uploadByteArrayAsObj(String bucketName, String fileName, byte[] bytes) throws StorageException;

    /**
     * 流式上传文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @param fileStream 文件输入流
     * @throws StorageException 存储异常
     */
    void uploadFileStream(String bucketName, String fileName, InputStream fileStream) throws StorageException;

    /**
     * 分块上传文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @param fileStream 文件输入流
     * @param chunkIndex 当前块的索引
     * @param totalChunks 总块数
     * @throws StorageException 存储异常
     */
    void uploadFileInChunks(String bucketName, String fileName, InputStream fileStream, int chunkIndex, int totalChunks) throws StorageException;

    /**
     * 直接下载文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @return 文件输入流
     * @throws StorageException 存储异常
     */
    InputStream downloadFileAsStream(String bucketName, String fileName) throws StorageException;

    /**
     * 流式下载文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @return 文件输入流
     * @throws StorageException 存储异常
     */
    byte[] downloadFileAsBytes(String bucketName, String fileName);

    /**
     * 生成预签名URL
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return 预签名URL
     * @throws StorageException 存储异常
     */
    URL generatePresignedUrl(String bucketName, String fileName) throws StorageException;

    /**
     * 分块下载文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @param chunkIndex 当前块的索引
     * @param chunkSize 每块的大小
     * @return 文件输入流
     * @throws StorageException 存储异常
     */
    InputStream downloadFileInChunks(String bucketName, String fileName, int chunkIndex, int chunkSize) throws StorageException;

    /**
     * 列出存储桶中的所有文件
     * @param bucketName 存储桶名称
     * @return 文件名称列表
     * @throws StorageException 存储异常
     */
    List<String> listFiles(String bucketName) throws StorageException;

    /**
     * 删除文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @throws StorageException 存储异常
     */
    void deleteFile(String bucketName, String fileName) throws StorageException;

    /**
     * 获取文件元数据
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @return 文件元数据
     * @throws StorageException 存储异常
     */
    Map<String, String> getFileMetadata(String bucketName, String fileName) throws StorageException;
}
