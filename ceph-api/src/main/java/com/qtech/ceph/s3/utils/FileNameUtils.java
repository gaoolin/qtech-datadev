package com.qtech.ceph.s3.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/10 22:11:02
 * desc   :
 */

public class FileNameUtils {

    /**
     * 处理文件名，如果文件名已经存在，则生成一个新的文件名。
     *
     * @param fileName   原文件名
     * @param fileExists 文件是否存在的标志
     * @return 包含原始文件名和新文件名的映射，如果文件名没有改变则返回 null
     */
    public static Map<String, String> getFileNameWithPossibleRename(String fileName, boolean fileExists) {
        if (!fileExists) {
            return null; // 文件名未变更
        }

        String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        String uuid = UUID.randomUUID().toString();
        String newFileName = nameWithoutExtension + "-" + uuid + extension;

        Map<String, String> responseData = new HashMap<>();
        responseData.put("originalFileName", fileName);
        responseData.put("newFileName", newFileName);
        return responseData;
    }
}