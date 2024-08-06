package com.qtech.ceph.file.service;

import com.ceph.fs.CephStat;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 09:55:51
 * desc   :
 */


public interface CephFileService {
    boolean mountCephFileSystem();

    String[] createDirectory(String path);

    String[] deleteDirectory(String path);

    CephStat getFileStatus(String path);

    String readFile(String path);

    boolean uploadFile(String filePath, String fileName);

    boolean uploadFileContent(InputStream inputStream, int fd, long fileLength) throws IOException;

    boolean downloadFile(String filePath, String fileName);

    boolean downloadFileContent(OutputStream outputStream, String fileName, long fileLength) throws IOException;

    boolean uploadFile(MultipartFile multipartFile);

    void downloadFile(HttpServletResponse response, String filePath) throws IOException;
}
