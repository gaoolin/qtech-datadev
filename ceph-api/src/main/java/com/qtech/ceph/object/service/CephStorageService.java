package com.qtech.ceph.object.service;

import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 09:20:28
 * desc   :  Ceph对象存储的服务
 */

public interface CephStorageService {

    List<Bucket> listAllBuckets();

    ListObjectsV2Response createBucketAndListObjects(String bucketName);

    ListObjectsV2Response listObjects(String bucketName);

    String createBucket(String bucketName);

    void deleteBucket(String bucketName);

    void uploadStringAsObj(String bucketName, String objName, String content);

    void setObjPublic(String bucketName, String objName);

    void downloadObj(String bucketName, String keyName, String dirName);

    void deleteObj(String bucketName, String objName);

    URL generatePresignedUrl(String bucketName, String keyName);

    URL uploadObjAndGetUrl(String bucketName, File obj, String keyName);

    void uploadInputStreamAsObj(String bucketName, String objName, InputStream input);

    void uploadByteArrayAsObj(String bucketName, String objName, byte[] contents);

    InputStream downloadObjAsInputStream(String bucketName, String objName);

    byte[] downloadObjAsByteArray(String bucketName, String objName);
}