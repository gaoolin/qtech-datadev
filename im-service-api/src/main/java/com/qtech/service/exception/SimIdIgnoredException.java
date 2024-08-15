package com.qtech.service.exception;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/15 10:26:04
 * desc   :  处理忽略simId异常
 */

public class SimIdIgnoredException extends RuntimeException {
    public SimIdIgnoredException() {
    }
    public SimIdIgnoredException(String message) {
        super(message);
    }
}