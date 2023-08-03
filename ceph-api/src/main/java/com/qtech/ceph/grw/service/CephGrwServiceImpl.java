package com.qtech.ceph.grw.service;

import com.alibaba.fastjson.JSON;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import com.qtech.ceph.common.DateUtils;
import com.qtech.ceph.grw.utils.Utils;
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
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/14 15:19:09
 * desc   :  对象存储相关操作
 */

@Service
public class CephGrwServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(CephGrwServiceImpl.class);

    @Autowired
    private AmazonS3 conn;

    /**
     * 【你的 access_key】
     */
    private static final String AWS_ACCESS_KEY = "XH084XXXXA3Y0EZT2CX";
    /**
     * 【你的 aws_secret_key】
     */
    private static final String AWS_SECRET_KEY = "rJ4Xs9wACXXXXXognDwEP31KmUzv1vV9M24BWT88";

    /**
     * 【你的 endpoint】
     */
    private static final String ENDPOINT = "http://127.0.0.1:7480";

    /*private static AmazonS3 conn;*/

    /**
     * 静态块：初始化S3的连接对象AmazonS3！ 需要3个参数：AWS_ACCESS_KEY，AWS_SECRET_KEY
     */
    /*static {
        InputStream inputStream = getResourceAsStream("properties/ceph.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        AWSCredentials awsCredentials = new BasicAWSCredentials(p.getProperty("ceph.accessKey"), p.getProperty("ceph.secretKey"));
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        conn = new AmazonS3Client(awsCredentials, clientConfig);
        conn.setEndpoint(p.getProperty("ceph.domain.ip"));
    }*/

    /**
     * 获取ceph的所有列表
     *
     * @return
     */
    public List<Bucket> getBucketList() {
        List<Bucket> buckets = conn.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName() + "\t" +
                    StringUtils.fromDate(bucket.getCreationDate()));
        }
        return buckets;
    }

    /**
     * 获取ceph的当天桶名称是否已创建
     *
     * @return
     */
    public boolean getBucketIsCreated() {
        List<Bucket> buckets = conn.listBuckets();
        for (Bucket bucket : buckets) {
            if (("bst-" + DateUtils.dateTime()).equals(bucket.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取ceph当前日期的桶名称
     *
     * @return bucket.getName()
     */
    public String getCurrentDateBucketName() {
        List<Bucket> buckets = conn.listBuckets();
        for (Bucket bucket : buckets) {
            if (("bst-" + DateUtils.dateTime()).equals(bucket.getName())) {
                return bucket.getName();
            }
        }
        return createBucket();
    }


    /**
     * 创建对象的bucket
     *
     * @param bucketName
     * @return
     */
    public ObjectListing getObjectListing(String bucketName) {
        Bucket bucket = conn.createBucket(bucketName);
        ObjectListing objects = conn.listObjects(bucket.getName());
        do {
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                System.out.println(objectSummary.getKey() + "\t" +
                        objectSummary.getSize() + "\t" +
                        StringUtils.fromDate(objectSummary.getLastModified()));
            }
            objects = conn.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());
        return objects;
    }

    /**
     * 创建对象bucket
     *
     * @param
     */
    public String createBucket() {
        String bucketName = "qtech-" + DateUtils.dateTime();
        Bucket bucket = conn.createBucket(bucketName);
        System.out.println(JSON.toJSONString(bucket));
        return bucketName;
    }

    public String createBucket(String bucketName) {
        Bucket bucket = conn.createBucket(bucketName);
        System.out.println(JSON.toJSONString(bucket));
        return "bucket " + bucketName + "successful created!";
    }

    /**
     * 删除对象bucket
     *
     * @param bucketName
     */
    public void deleteBucket(String bucketName) {
        conn.deleteBucket(bucketName);
    }

    /**
     * 上传字符串生成文件
     *
     * @param bucketName
     * @param fileName
     * @param text
     */
    public void uploadStream(String bucketName, String fileName, String text) {
        ByteArrayInputStream input = new ByteArrayInputStream(text.getBytes());
        PutObjectResult putObjectResult = conn.putObject(bucketName, fileName, input, new ObjectMetadata());
        System.out.println(JSON.toJSONString(putObjectResult));
    }

    /**
     * 修改文件权限 public
     *
     * @param bucketName
     * @param fileName
     */
    public void modifyPub(String bucketName, String fileName) {
        conn.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
    }

    /**
     * 下载
     *
     * @param bucketName
     * @param keyName
     * @param dirName
     */
    public void downloadFile(String bucketName, String keyName, String dirName) {
        conn.getObject(
                new GetObjectRequest(bucketName, keyName),
                new File(dirName)
        );
    }

    /**
     * 删除文件
     */
    public void deleteObject(String bucketName, String fileName) {
        conn.deleteObject(bucketName, fileName);
    }

    /**
     * 获取下载url 生成对象的下载 URLS (带签名和不带签名)
     *
     * @param bucketName
     * @param keyName
     * @return
     */
    public URL geturl(String bucketName, String keyName) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, keyName);
        return conn.generatePresignedUrl(request);
    }

    /**
     * 上传文件返回url
     *
     * @param bucketName
     * @param file
     * @param keyName
     * @return
     */
    public URL uploadFileToUrl(String bucketName, File file, String keyName) {
        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, keyName, file);
            conn.putObject(request);
        } catch (Exception e) {
            logger.info("上传文件异常：{}", e);
        }
        GeneratePresignedUrlRequest requests = new GeneratePresignedUrlRequest(bucketName, keyName);
        return conn.generatePresignedUrl(requests);
    }

    /**
     * 上传InputStream文件
     *
     * @param bucketName
     * @param fileName
     * @param input
     */
    public void uploadInputStream(String bucketName, String fileName, InputStream input) {
        PutObjectResult putObjectResult = conn.putObject(bucketName, fileName, input, new ObjectMetadata());
        System.out.println(JSON.toJSONString(putObjectResult));
    }


    /**
     * 上传文件字节流到ceph
     *
     * @param bucketName
     * @param fileName
     * @param contents
     */
    public void uploadByte(String bucketName, String fileName, byte[] contents) {
        try (ByteArrayInputStream input = new ByteArrayInputStream(contents)) {
            PutObjectResult putObjectResult = conn.putObject(bucketName, fileName, input, new ObjectMetadata());
            System.out.println(JSON.toJSONString(putObjectResult));
            ObjectListing objects = conn.listObjects(bucketName);
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                System.out.println("====" + objectSummary.getKey());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("文件上传到阿里云OSS服务报错!", e);
        }
        conn.deleteBucketLifecycleConfiguration("");
    }

    /**
     * 从ceph系统上下载流对象
     *
     * @param bucketName
     * @param
     */
    public InputStream readStreamObject(String bucketName, String fileName) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
        S3Object object = conn.getObject(getObjectRequest);
        System.out.println("Content-Type:" + object.getObjectMetadata().getContentType());
        return object.getObjectContent();
    }

    public byte[] downloadObjectByByte(String bucketName, String fileName) {
        InputStream inputStream = readStreamObject(bucketName, fileName);
        try {
            return Utils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
