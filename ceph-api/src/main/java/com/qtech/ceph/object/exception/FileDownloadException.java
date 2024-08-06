package com.qtech.ceph.object.exception;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 09:51:23
 * desc   :  Exception thrown when there is an error downloading a file.
 */


public class FileDownloadException extends StorageException {

    public FileDownloadException() {
        super("File download failed. Please check the file and bucket parameters.", 1002);
    }

    public FileDownloadException(String message) {
        super(message, 1002);
    }

    public FileDownloadException(String message, Throwable cause) {
        super(message, cause, 1002);
    }

    public FileDownloadException(Throwable cause) {
        super("File download failed.", cause, 1002);
    }
}
