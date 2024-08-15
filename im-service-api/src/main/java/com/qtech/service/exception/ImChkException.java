package com.qtech.service.exception;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/15 10:26:04
 * desc   :  处理点检查询时发生的异常
 */

public class ImChkException extends RuntimeException {
    public ImChkException() {
    }
    public ImChkException(String message) {
        super(message);
    }
}