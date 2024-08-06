package com.qtech.ceph.object.service;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;

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

    ObjectListing createBucketAndListObjects(String bucketName);

    ObjectListing listObjects(String bucketName);

    String createBucket(String bucketName);

    void deleteBucket(String bucketName);

    void uploadStringAsObj(String bucketName, String ObjName, String content);

    void setObjPublic(String bucketName, String ObjName);

    void downloadObj(String bucketName, String keyName, String dirName);

    void deleteObject(String bucketName, String ObjName);

    URL generatePresignedUrl(String bucketName, String keyName);

    URL uploadObjAndGetUrl(String bucketName, File Obj, String keyName);

    void uploadInputStreamAsObj(String bucketName, String ObjName, InputStream input);

    void uploadByteArrayAsObj(String bucketName, String ObjName, byte[] contents);

    InputStream downloadObjectAsInputStream(String bucketName, String ObjName);

    byte[] downloadObjectAsByteArray(String bucketName, String ObjName);
}
