package com.qtech.mq.common;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 08:39:05
 * desc   :
 */


public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String REDIS_OLP_CHECK_DUPLICATION_KEY_PREFIX = "qtech:wb:olp:check:duplication:";
    public static final String REDIS_OLP_CHECK_WB_OLP_KEY_PREFIX = "qtech:wb:olp:check:";
}
