package com.qtech.service.service.ocr;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/11 08:11:15
 * desc   :
 */


public interface OcrLabelService {
    String S3Obj(String bucketName, String fileName, String contents);
}
