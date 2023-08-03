package com.qtech.ceph.grw.exception;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/24 11:12:58
 * desc   :  异常类
 */


public class UploadFileException extends IllegalArgumentException {

    public UploadFileException() {
        super("上传文件的参数异常，请检查！");
    }
}
