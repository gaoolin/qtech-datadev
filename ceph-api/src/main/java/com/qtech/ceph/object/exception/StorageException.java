package com.qtech.ceph.object.exception;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 09:50:21
 * desc   :  exception class for all storage-related errors.
 */

public class StorageException extends RuntimeException {

    private final int errorCode;

    public StorageException(String message) {
        super(message);
        this.errorCode = 1000; // Default error code
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 1000; // Default error code
    }

    public StorageException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public StorageException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
