package com.qtech.ceph.s3.utils;

import java.time.Duration;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/09 10:28:51
 * desc   :  常量类
 */


public final class S3Constants {
    /**
     * 预签名 URL 的过期时间（单位：秒）。
     * <p>
     * 例如，设置为 3600 秒（1 小时）。
     */
    public static final Duration DEFAULT_SIGNATURE_DURATION = Duration.ofMinutes(5);
    public static final Duration MAX_SIGNATURE_DURATION = Duration.ofDays(7); // 10080 minutes
    public static final int EXPIRATION_DAYS = 30;
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    // 私有构造函数，防止实例化
    private S3Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}