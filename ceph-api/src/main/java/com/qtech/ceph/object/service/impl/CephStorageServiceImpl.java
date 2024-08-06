package com.qtech.ceph.object.service.impl;

import com.alibaba.fastjson.JSON;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import com.qtech.ceph.object.service.CephStorageService;
import com.qtech.ceph.object.utils.FileConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Service class for Ceph object storage operations.
 * Author: Gaozhilin
 * Email: gaoolin@gmail.com
 * Date: 2023/07/14
 */
@Service
public class CephStorageServiceImpl implements CephStorageService {

    private static final Logger logger = LoggerFactory.getLogger(CephStorageServiceImpl.class);

    @Autowired
    private AmazonS3 s3Client;

    /**
     * Get the list of all buckets.
     * Original method name: getBucketList
     *
     * @return List of buckets
     */
    @Override
    public List<Bucket> listAllBuckets() {
        try {
            List<Bucket> buckets = s3Client.listBuckets();
            for (Bucket bucket : buckets) {
                logger.info("Bucket Name: {}, Creation Date: {}", bucket.getName(), StringUtils.fromDate(bucket.getCreationDate()));
            }
            return buckets;
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a bucket and get the object listing.
     * Original method name: getObjectListing
     *
     * @param bucketName Name of the bucket
     * @return Object listing of the bucket
     */
    @Override
    public ObjectListing createBucketAndListObjects(String bucketName) {
        try {
            s3Client.createBucket(bucketName);
            return listObjects(bucketName);
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * List objects in a bucket.
     *
     * @param bucketName Name of the bucket
     * @return Object listing
     */
    @Override
    public ObjectListing listObjects(String bucketName) {
        try {
            ObjectListing objects = s3Client.listObjects(bucketName);
            do {
                objects.getObjectSummaries().forEach(objectSummary ->
                        logger.info("Object Key: {}, Size: {}, Last Modified: {}", objectSummary.getKey(), objectSummary.getSize(), StringUtils.fromDate(objectSummary.getLastModified())));
                objects = s3Client.listNextBatchOfObjects(objects);
            } while (objects.isTruncated());
            return objects;
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a bucket with the specified name.
     * Original method name: createBucket
     *
     * @param bucketName Name of the bucket
     * @return Success message
     */
    @Override
    public String createBucket(String bucketName) {
        try {
            Bucket bucket = s3Client.createBucket(bucketName);
            logger.info("Bucket created: {}", JSON.toJSONString(bucket));
            return "Bucket " + bucketName + " successfully created!";
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete a bucket by its name.
     * Original method name: deleteBucket
     *
     * @param bucketName Name of the bucket
     */
    @Override
    public void deleteBucket(String bucketName) {
        try {
            s3Client.deleteBucket(bucketName);
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Upload a string as a file to a bucket.
     * Original method name: uploadStream
     *
     * @param bucketName Name of the bucket
     * @param ObjName    Name of the file
     * @param content    Content of the file
     */
    @Override
    public void uploadStringAsObj(String bucketName, String ObjName, String content) {
        try (ByteArrayInputStream input = new ByteArrayInputStream(content.getBytes())) {
            PutObjectResult putObjectResult = s3Client.putObject(bucketName, ObjName, input, new ObjectMetadata());
            logger.info("Obj uploaded: {}", JSON.toJSONString(putObjectResult));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set file permissions to public.
     * Original method name: modifyPub
     *
     * @param bucketName Name of the bucket
     * @param ObjName    Name of the file
     */
    @Override
    public void setObjPublic(String bucketName, String ObjName) {
        try {
            s3Client.setObjectAcl(bucketName, ObjName, CannedAccessControlList.PublicRead);
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Download a file from a bucket to a local directory.
     * Original method name: downloadFile
     *
     * @param bucketName Name of the bucket
     * @param keyName    Key of the file
     * @param dirName    Local directory
     */
    @Override
    public void downloadObj(String bucketName, String keyName, String dirName) {
        // 确保目录存在
        File dir = new File(dirName);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + dirName);
            }
        }

        // 文件的目标路径
        File localFile = new File(dir, keyName);

        try {
            // 从S3存储桶下载文件
            s3Client.getObject(new GetObjectRequest(bucketName, keyName), localFile);
        } catch (SdkClientException e) {
            throw new RuntimeException("Failed to download file from bucket: " + bucketName, e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred during file download", e);
        }
    }


    /**
     * Delete an object from a bucket.
     * Original method name: deleteObject
     *
     * @param bucketName Name of the bucket
     * @param ObjName    Name of the file
     */
    @Override
    public void deleteObject(String bucketName, String ObjName) {
        try {
            s3Client.deleteObject(bucketName, ObjName);
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate a pre-signed URL for downloading an object.
     * Original method name: geturl
     *
     * @param bucketName Name of the bucket
     * @param keyName    Key of the file
     * @return Pre-signed URL
     */
    @Override
    public URL generatePresignedUrl(String bucketName, String keyName) {
        try {
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, keyName);
            return s3Client.generatePresignedUrl(request);
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Upload a file and return its URL.
     * Original method name: uploadFileToUrl
     *
     * @param bucketName Name of the bucket
     * @param Obj        file to upload
     * @param keyName    Key of the file
     * @return URL of the uploaded file
     */
    @Override
    public URL uploadObjAndGetUrl(String bucketName, File Obj, String keyName) {
        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, keyName, Obj);
            s3Client.putObject(request);
        } catch (Exception e) {
            logger.error("Error uploading Obj: {}", e.getMessage());
        }
        return generatePresignedUrl(bucketName, keyName);
    }

    /**
     * Upload an InputStream as a file to a bucket.
     * Original method name: uploadInputStream
     *
     * @param bucketName Name of the bucket
     * @param ObjName    Name of the file
     * @param input      InputStream of the file
     */
    @Override
    public void uploadInputStreamAsObj(String bucketName, String ObjName, InputStream input) {
        try {
            PutObjectResult putObjectResult = s3Client.putObject(bucketName, ObjName, input, new ObjectMetadata());
            logger.info("Obj uploaded: {}", JSON.toJSONString(putObjectResult));
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Upload a byte array as a file to a bucket.
     * Original method name: uploadByte
     *
     * @param bucketName Name of the bucket
     * @param ObjName    Name of the file
     * @param contents   Byte array content of the Obj
     */
    @Override
    public void uploadByteArrayAsObj(String bucketName, String ObjName, byte[] contents) {
        try (ByteArrayInputStream input = new ByteArrayInputStream(contents)) {
            PutObjectResult putObjectResult = s3Client.putObject(bucketName, ObjName, input, new ObjectMetadata());
            logger.info("Obj uploaded: {}", JSON.toJSONString(putObjectResult));
            listObjects(bucketName).getObjectSummaries().forEach(objectSummary ->
                    logger.info("Object Key: {}", objectSummary.getKey()));
        } catch (IOException e) {
            throw new IllegalStateException("Error uploading byte array to Ceph", e);
        }
    }

    /**
     * Read an object from a bucket as an InputStream.
     * Original method name: readStreamObject
     *
     * @param bucketName Name of the bucket
     * @param ObjName    Name of the Obj
     * @return InputStream of the object
     */
    @Override
    public InputStream downloadObjectAsInputStream(String bucketName, String ObjName) {
        try {
            S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, ObjName));
            return object.getObjectContent();
        } catch (SdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Download an object from a bucket as a byte array.
     * Original method name: downloadObjectAsByteArray
     *
     * @param bucketName Name of the bucket
     * @param ObjName    Name of the Obj
     * @return Byte array of the object content
     */
    @Override
    public byte[] downloadObjectAsByteArray(String bucketName, String ObjName) {
        S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, ObjName));
        try (S3ObjectInputStream inputStream = object.getObjectContent()) {
            return FileConvertor.readInputStreamToByte(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Error downloading object as byte array from Ceph", e);
        }
    }
}
