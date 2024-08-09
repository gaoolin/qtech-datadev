package com.qtech.ceph.s3.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/08 14:47:42
 * desc   :
 */

@Component
public class S3Utils {
    private static final Logger logger = LoggerFactory.getLogger(S3Utils.class);
    public static String S3_ACCESS_KEY_ID = null;
    public static String S3_SECRET_KEY = null;
    public static String S3_URI = null;
    public static String S3_BUCKET = null;
    private static S3Client s3Client;
    private static S3Presigner s3Presigner;
    @Value("${aws.s3.accessKey}")
    private String accessKeyId;
    @Value("${aws.s3.secretKey}")
    private String secretKey;
    @Value("${aws.s3.endpoint}")
    private String s3Uri;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    /**
     * @param
     * @return software.amazon.awssdk.services.s3.S3Client
     * @description: 获取S3客户端对象
     * @author: bug
     * @date: 2022/6/23 19:45
     */
    public static synchronized S3Client getS3Client() {
        if (null == s3Client) {
            s3Client = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(S3_ACCESS_KEY_ID, S3_SECRET_KEY))).endpointOverride(URI.create(S3_URI)).serviceConfiguration(item -> item.pathStyleAccessEnabled(true).checksumValidationEnabled(false)).region(Region.AP_SOUTHEAST_1).build();
        }

        return s3Client;
    }

    /**
     * @param
     * @return software.amazon.awssdk.services.s3.presigner.S3Presigner
     * @description: 获取预签名对象
     * @author: bug
     * @date: 2022/6/23 19:46
     */
    public static synchronized S3Presigner getS3PreSigner() {
        if (null == s3Presigner) {
            s3Presigner = S3Presigner.builder().credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(S3_ACCESS_KEY_ID, S3_SECRET_KEY))).endpointOverride(URI.create(S3_URI)).serviceConfiguration(S3Configuration.builder().checksumValidationEnabled(false).pathStyleAccessEnabled(true).build()).region(Region.AP_SOUTHEAST_1).build();
        }
        return s3Presigner;
    }


    /**
     * @param multipartFile 文件对象
     * @param pathName      目标目录
     * @return java.lang.String
     * @description: 根据文件对象上传文件
     * @author: bug
     * @date: 2022/6/23 19:38
     */
    public static String uploadPublicFile(MultipartFile multipartFile, String pathName) {
        S3Client s3Client = getS3Client();
        String s3FileFullPath = getFileName(multipartFile, pathName);
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(S3_BUCKET).contentType(multipartFile.getContentType()).contentLength(multipartFile.getSize()).key(s3FileFullPath).acl(ObjectCannedACL.PUBLIC_READ).build();
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));
            return s3FileFullPath;
        } catch (Exception e) {
            logger.error("上传文件到S3失败 异常：{}", e);
        }
        return null;
    }

    /**
     * @param multipartFile 上传文件
     * @param pathName      目标目录
     * @return java.lang.String
     * @description: 根据文件对象和目标目录生成新的文件地址
     * @author: bug
     * @date: 2022/6/23 19:40
     */
    private static String getFileName(MultipartFile multipartFile, String pathName) {
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

        return getFileName(suffix, pathName);
    }

    /**
     * @param suffix   文件后缀
     * @param pathName 目标目录 test
     * @return java.lang.String   test/2022/06
     * @description: 根据文件后缀和目标目录生成新的文件地址
     * @author: bug
     * @date: 2022/6/23 19:41
     */
    private static String getFileName(String suffix, String pathName) {
        // 生成随机文件名
        String localFileName = UUID.randomUUID() + "." + suffix;
        Date date = new Date();
        SimpleDateFormat formatter_yyyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatter_MM = new SimpleDateFormat("MM");
        // 在随机名前加上年月

        return pathName + "/" + formatter_yyyy.format(date) + "/" + formatter_MM.format(date) + "/" + localFileName;
    }

    /**
     * @param keyName               key名称: test/2022/06/123.pdf
     * @param signatureDurationTime 有效期 单位：秒
     * @return java.lang.String
     * @description: 生成预签名URL
     * @author: bug
     * @date: 2022/6/23 19:42
     */
    public static String getPreSignatureUrl(String keyName, Integer signatureDurationTime) {
        String preSignatureUrl = "";
        try {
            S3Presigner s3PreSigner = getS3PreSigner();
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(S3_BUCKET).key(keyName).build();
            // 设置预签名URL可访问时间
            signatureDurationTime = Optional.ofNullable(signatureDurationTime).map(item -> {
                if (item.intValue() > 10080) {
                    item = 10080;
                }
                return item;
            }).orElse(10);
            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder().signatureDuration(Duration.ofMinutes(signatureDurationTime)).getObjectRequest(getObjectRequest).build();

            PresignedGetObjectRequest presignedGetObjectRequest = s3PreSigner.presignGetObject(getObjectPresignRequest);

            preSignatureUrl = String.valueOf(presignedGetObjectRequest.url());

        } catch (Exception e) {
            logger.error("生成预签名URL失败，异常：{}", e);
        }
        return preSignatureUrl;
    }

    /**
     * 获取S3中对象的信息，最多只能返回1000个
     *
     * @return
     */
    public static List<Map<String, Object>> listObject() {
        ListObjectsRequest listObjects = ListObjectsRequest.builder().bucket(S3_BUCKET).build();
        S3Client s3Client = getS3Client();
        ListObjectsResponse res = s3Client.listObjects(listObjects);
        List<S3Object> objects = res.contents();

        List<Map<String, Object>> result = Lists.newArrayList();
        for (ListIterator iterVals = objects.listIterator(); iterVals.hasNext(); ) {
            S3Object myValue = (S3Object) iterVals.next();
            Map<String, Object> map = Maps.newHashMap();
            map.put("objectKey", myValue.key());
            map.put("objectSize(/KBs)", myValue.size() / 1024);
            map.put("ownerId", myValue.owner().id());
            map.put("ownerDisplayName", myValue.owner().displayName());
            result.add(map);
        }
        return result;
    }

    /**
     * 获取S3中对象的信息，最多只能返回1000个
     *
     * @param delimiter 分隔符号
     * @return
     */
    public static List<Map<String, Object>> listObjectByDelimiter(String delimiter, Integer maxKeys) {
        // 限制返回对象信息数量
        maxKeys = Optional.ofNullable(maxKeys).map(item -> {
            if (item.intValue() > 1000) {
                item = 1000;
            }
            return item;
        }).orElse(1000);

        ListObjectsRequest listObjects = ListObjectsRequest.builder().bucket(S3_BUCKET).delimiter(delimiter).maxKeys(maxKeys).build();
        S3Client s3Client = getS3Client();
        ListObjectsResponse res = s3Client.listObjects(listObjects);
        List<S3Object> objects = res.contents();

        List<Map<String, Object>> result = Lists.newArrayList();
        for (ListIterator iterVals = objects.listIterator(); iterVals.hasNext(); ) {
            S3Object myValue = (S3Object) iterVals.next();
            Map<String, Object> map = Maps.newHashMap();
            map.put("objectKey", myValue.key());
            map.put("objectSize(/KBs)", myValue.size() / 1024);
            map.put("ownerId", myValue.owner().id());
            map.put("ownerDisplayName", myValue.owner().displayName());
            result.add(map);
        }
        return result;
    }

    /**
     * 获取S3中对象的信息，最多只能返回1000个
     *
     * @param prefix 前缀
     * @return
     */
    public static List<Map<String, Object>> listObjectByPrefix(String prefix, Integer maxKeys) {
        // 限制返回对象信息数量
        maxKeys = Optional.ofNullable(maxKeys).map(item -> {
            if (item.intValue() > 1000) {
                item = 1000;
            }
            return item;
        }).orElse(1000);
        ListObjectsRequest listObjects = ListObjectsRequest.builder().bucket(S3_BUCKET).prefix(prefix).maxKeys(maxKeys).build();
        S3Client s3Client = getS3Client();
        ListObjectsResponse res = s3Client.listObjects(listObjects);
        List<S3Object> objects = res.contents();

        List<Map<String, Object>> result = Lists.newArrayList();
        for (ListIterator iterVals = objects.listIterator(); iterVals.hasNext(); ) {
            S3Object myValue = (S3Object) iterVals.next();
            Map<String, Object> map = Maps.newHashMap();
            map.put("objectKey", myValue.key());
            map.put("objectSize(/KBs)", myValue.size() / 1024);
            map.put("ownerId", myValue.owner().id());
            map.put("ownerDisplayName", myValue.owner().displayName());
            result.add(map);
        }
        return result;
    }

    /**
     * 获取S3中对象的信息，最多只能返回1000个
     *
     * @param delimiter 分隔符
     * @param prefix    前缀
     * @param maxKeys   返回最大的对象信息 1000个
     * @return
     */
    public static List<Map<String, Object>> listObjectByDelimiterWithPrefix(String delimiter, String prefix, Integer maxKeys) {
        // 限制返回对象信息数量
        maxKeys = Optional.ofNullable(maxKeys).map(item -> {
            if (item.intValue() > 1000) {
                item = 1000;
            }
            return item;
        }).orElse(1000);

        ListObjectsRequest listObjects = ListObjectsRequest.builder().bucket(S3_BUCKET).delimiter(delimiter).prefix(prefix).maxKeys(maxKeys).build();
        S3Client s3Client = getS3Client();
        ListObjectsResponse res = s3Client.listObjects(listObjects);
        List<S3Object> objects = res.contents();

        List<Map<String, Object>> result = Lists.newArrayList();
        for (ListIterator iterVals = objects.listIterator(); iterVals.hasNext(); ) {
            S3Object myValue = (S3Object) iterVals.next();
            Map<String, Object> map = Maps.newHashMap();
            map.put("objectKey", myValue.key());
            map.put("objectSize(/KBs)", myValue.size() / 1024);
            map.put("ownerId", myValue.owner().id());
            map.put("ownerDisplayName", myValue.owner().displayName());
            result.add(map);
        }
        return result;
    }

    @PostConstruct
    public void init() {
        S3_ACCESS_KEY_ID = accessKeyId;
        S3_SECRET_KEY = secretKey;
        S3_URI = s3Uri;
        S3_BUCKET = bucketName;
    }
}
