package com.qtech.ceph.object.exception;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 09:52:24
 * desc   :  Exception thrown when there is an error deleting a file.
 */

public class FileDeletionException extends StorageException {

    public FileDeletionException() {
        super("File deletion failed. Please check the file and bucket parameters.", 1003);
    }

    public FileDeletionException(String message) {
        super(message, 1003);
    }

    public FileDeletionException(String message, Throwable cause) {
        super(message, cause, 1003);
    }

    public FileDeletionException(Throwable cause) {
        super("File deletion failed.", cause, 1003);
    }
}
