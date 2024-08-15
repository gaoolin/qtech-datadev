package com.qtech.service.exception;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/03/21 10:05:29
 * desc   :
 */


public class TooManyResultsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TooManyResultsException() {}

    public TooManyResultsException(String message) {
        super(message);
    }

    public TooManyResultsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyResultsException(Throwable cause) {
        super(cause);
    }
}
