package com.qtech.mq.common;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 08:39:05
 * desc   :
 */


public class Constants {
    public static final String REDIS_OLP_CHECK_DUPLICATION_KEY_PREFIX = "qtech:im:olp_chk:";
    public static final String KAFKA_TOPIC = "qtech_im_wb_olp_chk_topic";
    public static final String REDIS_OLP_RAW_DUPLICATION_KEY_PREFIX = "qtech:im:olp_raw:";
    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
