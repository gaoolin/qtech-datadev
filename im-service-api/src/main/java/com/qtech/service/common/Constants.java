package com.qtech.service.common;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:42:57
 * desc   :  常量类
 */


public class Constants {

    public static final String CEPH_HTTP_URL = "http://localhost:8080/s3/files/upload/json?bucketName=%s&fileName=%s";
    public static final String CEPH_HTTP_URL_TEST = "http://localhost:8080/s3/files/upload/bytes?bucketName=%s&fileName=%s";
    public static final String CEPH_HTTP_URL_DEV = "http://10.170.6.40:31555/s3/files/upload/bytes?bucketName=%s&fileName=%s";
    public static final String CEPH_HTTP_URL_PROD = "http://ceph-api-svc.ceph-api:8080/s3/files/upload/bytes?bucketName=%s&fileName=%s";
    public static final String OCR_HTTP_URL = "http://im-ocr-app.qtech-im-service:5000/ocr/label";
    public static final String OCR_HTTP_URL_TEST = "http://127.0.0.1:5000/ocr/label";
    public static final String OCR_HTTP_URL_DEV = "http://10.170.6.40:30113/ocr/label";
    public static final String OCR_HTTP_URL_PROD = "http://im-ocr-app.qtech-im-service:5000/ocr/label";
    public static final String WB_OLP_CHECK_REDIS_KEY_PREFIX = "qtech:chk:olp:check:";
    public static final String WB_COMPARISON_REDIS_JOB_STAT_KEY_PREFIX = "chk:comparison:job:";
    public static final String REDIS_JOB_RUN_DT_KEY_PREFIX = "qtech:datadev:job:run:dt:";
    public static final String REDIS_JOB_RUN_STAT_KEY_PREFIX = "qtech:datadev:job:run:stat:";
    private Constants() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
}