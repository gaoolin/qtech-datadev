package com.qtech.ocr;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/09 17:17:07
 * desc   :
 */


public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    public static final String CEPH_HTTP_URL = "http://localhost:8080/s3/files/upload/json?bucketName=%s&fileName=%s";
    public static final String CEPH_HTTP_URL_TEST = "http://localhost:8080/s3/files/upload/bytes?bucketName=%s&fileName=%s";
    public static final String CEPH_HTTP_URL_DEV = "http://10.170.6.40:31555/s3/files/upload/bytes?bucketName=%s&fileName=%s";
    public static final String CEPH_HTTP_URL_PROD = "http://ceph-api-svc.ceph-api:8080/s3/files/upload/bytes?bucketName=%s&fileName=%s";
    public static final String OCR_HTTP_URL = "http://127.0.0.1:5000/ocr/label";
    public static final String OCR_HTTP_URL_TEST = "http://127.0.0.1:5000/ocr/label";
    public static final String OCR_HTTP_URL_DEV = "http://10.170.6.40:30113/ocr/label";
    public static final String OCR_HTTP_URL_PROD = "http://ocr-label.qtech-ocr-app:5000/ocr/label";
}
