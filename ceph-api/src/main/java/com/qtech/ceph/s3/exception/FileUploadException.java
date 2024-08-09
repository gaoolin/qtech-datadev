package com.qtech.ceph.s3.exception;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 09:53:31
 * desc   :  Exception thrown when there is an error uploading a file.
 */

public class FileUploadException extends StorageException {

    public FileUploadException() {
        super("File upload failed. Please check the upload parameters.", 1001);
    }

    public FileUploadException(String message) {
        super(message, 1001);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause, 1001);
    }

    public FileUploadException(Throwable cause) {
        super("File upload failed.", cause, 1001);
    }
}
