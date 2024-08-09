package com.qtech.ceph.file.service.impl;

import com.ceph.fs.CephMount;
import com.ceph.fs.CephStat;
import com.qtech.ceph.file.service.CephFileService;
import com.qtech.ceph.s3.exception.FileDownloadException;
import com.qtech.ceph.s3.exception.FileUploadException;
import com.qtech.ceph.s3.exception.StorageServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 10:03:51
 * desc   :  Implementation of file storage services for Ceph file system.
 */


/**
 * 实现了 CephFileService 接口，提供对 Ceph 文件系统的操作服务。
 */
@Service("cephFileService")
public class CephFileServiceImpl implements CephFileService {

    private static final Logger logger = LoggerFactory.getLogger(CephFileServiceImpl.class);
    private static final int BUFFER_SIZE = 1024;

    private CephMount cephMount;

    /**
     * 挂载 Ceph 文件系统。
     *
     * @return 如果挂载成功则返回 true，否则返回 false
     */
    @Override
    public boolean mountCephFileSystem() {
        try {
            if (cephMount == null) {
                cephMount = new CephMount("admin");
                cephMount.conf_set("mon_host", "10.96.179.58;10.96.141.70;10.96.137.48");
                cephMount.conf_set("key", "AQDztv1j2RZSOxAAb+KA/gKvLu8e4P1GR+C3AQ==");
                cephMount.mount("/");
                return true;
            }
        } catch (Exception e) {
            logger.error("Failed to mount Ceph file system.", e);
            throw new StorageServiceException("Failed to mount Ceph file system.", e);
        }
        return false;
    }

    /**
     * 创建指定路径的目录。
     *
     * @param path 目录路径
     * @return 创建后的目录列表，如果文件系统未挂载则返回 null
     */
    @Override
    public String[] createDirectory(String path) {
        try {
            if (cephMount == null) {
                logger.warn("Ceph file system not mounted.");
                return null;
            }
            cephMount.mkdirs(path, 0777);
            return cephMount.listdir("/");
        } catch (Exception e) {
            logger.error("Failed to create directory.", e);
            throw new StorageServiceException("Failed to create directory.", e);
        }
    }

    /**
     * 删除指定路径的目录。
     *
     * @param path 目录路径
     * @return 删除后的目录列表，如果文件系统未挂载则返回 null
     */
    @Override
    public String[] deleteDirectory(String path) {
        try {
            if (cephMount == null) {
                logger.warn("Ceph file system not mounted.");
                return null;
            }
            cephMount.rmdir(path);
            return cephMount.listdir("/");
        } catch (Exception e) {
            logger.error("Failed to delete directory.", e);
            throw new StorageServiceException("Failed to delete directory.", e);
        }
    }

    /**
     * 获取指定路径的文件状态。
     *
     * @param path 文件路径
     * @return 文件状态，如果文件系统未挂载则返回 null
     */
    @Override
    public CephStat getFileStatus(String path) {
        try {
            if (cephMount == null) {
                logger.warn("Ceph file system not mounted.");
                return null;
            }
            CephStat stat = new CephStat();
            cephMount.lstat(path, stat);
            return stat;
        } catch (Exception e) {
            logger.error("Failed to get file status.", e);
            throw new StorageServiceException("Failed to get file status.", e);
        }
    }

    /**
     * 读取指定路径的文件内容。
     *
     * @param path 文件路径
     * @return 文件内容，如果文件系统未挂载则返回 null
     */
    @Override
    public String readFile(String path) {
        try {
            if (cephMount == null) {
                logger.warn("Ceph file system not mounted.");
                return null;
            }
            CephStat stat = new CephStat();
            int fd = cephMount.open(path, CephMount.O_RDONLY, 0);
            cephMount.fstat(fd, stat);

            byte[] buffer = new byte[(int) stat.size];
            cephMount.read(fd, buffer, stat.size, 0);
            cephMount.close(fd);
            return new String(buffer);
        } catch (Exception e) {
            logger.error("Failed to read file.", e);
            throw new StorageServiceException("Failed to read file.", e);
        }
    }

    /**
     * 上传指定路径的文件到 Ceph 文件系统。
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return 上传是否成功
     */
    @Override
    public boolean uploadFile(String filePath, String fileName) {
        if (cephMount == null) {
            logger.warn("Ceph file system not mounted.");
            return false;
        }

        File file = new File(filePath, fileName);
        if (!file.exists()) {
            logger.warn("File does not exist: {}", file.getAbsolutePath());
            return false;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            String[] existingFiles = cephMount.listdir("/");
            boolean fileExists = Arrays.asList(existingFiles).contains(fileName);

            if (!fileExists) {
                int fd = cephMount.open(fileName, CephMount.O_CREAT | CephMount.O_RDWR, 0);
                return uploadFileContent(fis, fd, file.length());
            } else {
                CephStat stat = new CephStat();
                cephMount.stat(fileName, stat);
                try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                    raf.seek(stat.size);
                    return uploadFileContent(fis, cephMount.open(fileName, CephMount.O_RDWR, 0), file.length());
                }
            }
        } catch (Exception e) {
            logger.error("Failed to upload file.", e);
            throw new FileUploadException("Failed to upload file.", e);
        }
    }

    /**
     * 上传文件内容到指定的文件描述符。
     *
     * @param inputStream 输入流
     * @param fd 文件描述符
     * @param fileLength 文件长度
     * @return 上传是否成功
     * @throws IOException 如果发生 I/O 错误
     */
    @Override
    public boolean uploadFileContent(InputStream inputStream, int fd, long fileLength) throws IOException {
        long uploadedLength = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            cephMount.write(fd, buffer, length, uploadedLength);
            uploadedLength += length;
            logProgress(uploadedLength, fileLength);
            if (uploadedLength == fileLength) break;
        }
        cephMount.fchmod(fd, 0666);
        cephMount.close(fd);
        return true;
    }

    /**
     * 下载指定路径的文件到指定目录。
     *
     * @param filePath 目录路径
     * @param fileName 文件名
     * @return 下载是否成功
     */
    @Override
    public boolean downloadFile(String filePath, String fileName) {
        if (cephMount == null) {
            logger.warn("Ceph file system not mounted.");
            return false;
        }

        File file = new File(filePath, fileName);

        try {
            CephStat stat = new CephStat();
            cephMount.stat(fileName, stat);
            long fileLength = stat.size;

            try (FileOutputStream fos = new FileOutputStream(file)) {
                return downloadFileContent(fos, fileName, fileLength);
            }
        } catch (Exception e) {
            logger.error("Failed to download file.", e);
            throw new FileDownloadException("Failed to download file.", e);
        }
    }

    /**
     * 从 Ceph 文件系统下载文件内容并写入指定的输出流。
     *
     * @param outputStream 输出流，用于接收文件内容
     * @param fileName 文件名
     * @param fileLength 文件长度
     * @return 是否成功下载文件内容
     * @throws IOException 如果发生 I/O 错误
     */
    @Override
    public boolean downloadFileContent(OutputStream outputStream, String fileName, long fileLength) throws IOException {
        if (cephMount == null) {
            throw new IOException("Ceph file system not mounted.");
        }

        int bufferSize = BUFFER_SIZE;
        byte[] buffer = new byte[bufferSize];
        long downloadedLength = 0;
        int fd = cephMount.open(fileName, CephMount.O_RDONLY, 0);

        try {
            while (downloadedLength < fileLength) {
                // Read data into buffer
                long bytesRead = cephMount.read(fd, buffer, bufferSize, downloadedLength);
                if (bytesRead == -1) break; // End of file

                // Write buffer to output stream
                outputStream.write(buffer, 0, (int) bytesRead);
                downloadedLength += bytesRead;
                logProgress(downloadedLength, fileLength);

                // If we have read the entire file, break out of the loop
                if (downloadedLength >= fileLength) {
                    break;
                }
            }

            // Ensure output stream is flushed
            outputStream.flush();
            return true;
        } finally {
            cephMount.close(fd);
        }
    }

    /**
     * 上传 MultipartFile 到 Ceph 文件系统。
     *
     * @param multipartFile MultipartFile 对象
     * @return 上传是否成功
     */
    @Override
    public boolean uploadFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null) {
            logger.warn("File name is null.");
            return false;
        }

        if (cephMount == null) {
            logger.warn("Ceph file system not mounted.");
            return false;
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            String[] existingFiles = cephMount.listdir("/");
            boolean fileExists = Arrays.asList(existingFiles).contains(fileName);

            int fd;
            if (!fileExists) {
                fd = cephMount.open(fileName, CephMount.O_CREAT | CephMount.O_RDWR, 0);
            } else {
                CephStat stat = new CephStat();
                cephMount.stat(fileName, stat);
                fd = cephMount.open(fileName, CephMount.O_RDWR, 0);
                try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
                    fos.getChannel().position(stat.size);
                }
            }
            return uploadFileContent(inputStream, fd, multipartFile.getSize());
        } catch (Exception e) {
            logger.error("Failed to upload file.", e);
            throw new FileUploadException("Failed to upload file.", e);
        }
    }

    /**
     * 从 Ceph 文件系统下载文件并将其写入 HttpServletResponse。
     *
     * @param response HttpServletResponse 对象，用于返回文件
     * @param filePath 文件路径
     * @throws IOException 如果发生 I/O 错误
     */
    @Override
    public void downloadFile(HttpServletResponse response, String filePath) throws IOException {
        if (cephMount == null) {
            logger.warn("Ceph file system not mounted.");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ceph file system not mounted.");
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            logger.warn("File does not exist: {}", file.getAbsolutePath());
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            response.setContentLengthLong(file.length());

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            response.getOutputStream().flush();
        } catch (Exception e) {
            logger.error("Failed to download file.", e);
            throw new IOException("Failed to download file.", e);
        }
    }

    /**
     * 记录文件操作的进度。
     *
     * @param current 当前已处理的字节数
     * @param total 总字节数
     */
    private void logProgress(long current, long total) {
        logger.info("Progress: {}% ({}/{})", (current * 100 / total), current, total);
    }
}

/*
注释说明
mountCephFileSystem()：尝试挂载 Ceph 文件系统。如果成功挂载则返回 true，否则返回 false。
createDirectory(String path)：创建一个新的目录，并返回文件系统根目录下的文件和目录列表。如果 Ceph 文件系统未挂载则返回 null。
deleteDirectory(String path)：删除指定路径的目录，并返回文件系统根目录下的文件和目录列表。如果 Ceph 文件系统未挂载则返回 null。
getFileStatus(String path)：获取指定路径的文件状态。如果 Ceph 文件系统未挂载则返回 null。
readFile(String path)：读取指定路径的文件内容，并返回内容字符串。如果 Ceph 文件系统未挂载则返回 null。
uploadFile(String filePath, String fileName)：上传指定路径的文件到 Ceph 文件系统。如果文件已存在，则从文件末尾继续上传。返回上传是否成功。
uploadFileContent(InputStream inputStream, int fd, long fileLength)：将文件内容从输入流上传到指定的文件描述符。处理文件的逐块上传，并记录上传进度。
downloadFile(String filePath, String fileName)：从 Ceph 文件系统下载指定路径的文件到指定目录。如果文件已存在，则从文件末尾继续下载。返回下载是否成功。
downloadFileContent(OutputStream outputStream, String fileName, long fileLength)：从 Ceph 文件系统下载文件内容并写入输出流。处理文件的逐块下载，并记录下载进度。
uploadFile(MultipartFile multipartFile)：上传 MultipartFile 对象到 Ceph 文件系统。如果文件已存在，则从文件末尾继续上传。返回上传是否成功。
downloadFile(HttpServletResponse response, String filePath)：将 Ceph 文件系统中的文件下载到 HttpServletResponse 中。处理文件的逐块读取，并设置响应头。
logProgress(long current, long total)：记录文件操作的进度，输出当前已处理的字节数和总字节数的百分比。
 */