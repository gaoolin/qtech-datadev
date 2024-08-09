package com.qtech.ceph.s3.exception;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 09:52:52
 * desc   :  thrown when there is an error with the storage service itself.
 */

public class StorageServiceException extends StorageException {

    public StorageServiceException() {
        super("Storage service is unavailable or encountered an error.", 1004);
    }

    public StorageServiceException(String message) {
        super(message, 1004);
    }

    public StorageServiceException(String message, Throwable cause) {
        super(message, cause, 1004);
    }

    public StorageServiceException(Throwable cause) {
        super("Storage service is unavailable.", cause, 1004);
    }
}
