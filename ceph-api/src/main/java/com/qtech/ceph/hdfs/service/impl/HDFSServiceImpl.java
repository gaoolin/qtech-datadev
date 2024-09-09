package com.qtech.ceph.hdfs.service.impl;

import com.qtech.ceph.hdfs.service.IHDFSService;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/02 08:51:49
 * desc   :
 */

@Service
public class HDFSServiceImpl implements IHDFSService {

    // 全局变量定义
    private static final int bufferSize = 1024 * 1024 * 64;

    // 日志记录服务
    private static final Logger logger = LoggerFactory.getLogger(HDFSServiceImpl.class);

    @Resource
    private FileSystem fileSystem;

    @PreDestroy
    public void CloseFile() throws IOException {
        fileSystem.close();
        logger.info("FileSystem关闭");
    }

    /**
     * 创建的文件夹权限不够，需要设置权限问题
     *
     * @param
     * @return
     */
    @Override
    public boolean mkdirFolder(String path) {
        boolean target = false;
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (existFile(path)) {
            return true;
        }
        Path src = new Path(path);
        try {
            target = fileSystem.mkdirs(src);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return target;
    }

    @Override
    public boolean existFile(String path) {
        boolean target = false;

        if (StringUtils.isEmpty(path)) {
            return target;
        }
        Path src = new Path(path);
        try {
            target = fileSystem.exists(src);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return target;
    }

    @Override
    public List<Map<String, Object>> readCatalog(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }

        // 目标路径
        Path newPath = new Path(path);
        FileStatus[] statusList = null;
        try {
            statusList = fileSystem.listStatus(newPath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        List<Map<String, Object>> list = new ArrayList<>();
        if (null != statusList && statusList.length > 0) {
            for (FileStatus fileStatus : statusList) {
                Map<String, Object> map = new HashMap<>();
                map.put("filePath", fileStatus.getPath());
                map.put("fileStatus", fileStatus.toString());
                list.add(map);
            }
            return list;
        } else {
            return null;
        }
    }

    @Override
    public void createFile(String path, MultipartFile file) {

        // 检查路径是否为空
        if (StringUtils.isEmpty(path)) {
            return;
        }
        // 获取文件的原始名称
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name is missing or empty.");
        }
        // 构建新的HDFS路径
        Path newPath = new Path(path + "/" + fileName);
        // 打开一个输出流
        FSDataOutputStream outputStream;
        try {
            outputStream = fileSystem.create(newPath);
            // 将文件内容写入HDFS
            byte[] bytes = file.getBytes();
            outputStream.write(bytes);
            // 关闭输出流
            outputStream.close();
        } catch (IOException e) {
            // 记录错误日志
            logger.error("Failed to create file in HDFS: {}", newPath, e);
            throw new RuntimeException("Failed to create file in HDFS.", e);
        }
    }

    @Override
    public String readFileContent(String path) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        // 目标路径
        Path srcPath = new Path(path);
        FSDataInputStream inputStream = null;
        try {
            inputStream = fileSystem.open(srcPath);
            // 防止中文乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = "";
            while ((lineTxt = reader.readLine()) != null) {
                sb.append(lineTxt);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return sb.toString();
    }

    @Override
    public List<Map<String, String>> listFile(String path) {
        List<Map<String, String>> returnList = new ArrayList<>();
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        // 目标路径
        Path srcPath = new Path(path);
        // 递归找到所有文件
        try {
            RemoteIterator<LocatedFileStatus> filesList = fileSystem.listFiles(srcPath, true);
            while (filesList.hasNext()) {
                LocatedFileStatus next = filesList.next();
                String fileName = next.getPath().getName();
                Path filePath = next.getPath();
                Map<String, String> map = new HashMap<>();
                map.put("fileName", fileName);
                map.put("filePath", filePath.toString());
                returnList.add(map);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return returnList;
    }

    @Override
    public boolean renameFile(String oldName, String newName) {
        boolean target = false;
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
            return false;
        }
        // 原文件目标路径
        Path oldPath = new Path(oldName);
        // 重命名目标路径
        Path newPath = new Path(newName);
        try {
            target = fileSystem.rename(oldPath, newPath);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return target;
    }

    @Override
    public boolean deleteFile(String path) {
        boolean target = false;
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (!existFile(path)) {
            return false;
        }
        Path srcPath = new Path(path);
        try {
            target = fileSystem.deleteOnExit(srcPath);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return target;
    }

    @Override
    public void uploadFile(String path, String uploadPath) {
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(uploadPath)) {
            return;
        }
        // 上传路径
        Path clientPath = new Path(path);
        // 目标路径
        Path serverPath = new Path(uploadPath);

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        try {
            fileSystem.copyFromLocalFile(false, clientPath, serverPath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 下载到服务器
     */
    @Override
    public void downloadFile(String path, String downloadPath) {
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(downloadPath)) {
            return;
        }
        // 上传路径
        Path clientPath = new Path(path);
        // 目标路径
        Path serverPath = new Path(downloadPath);

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        try {
            fileSystem.copyToLocalFile(false, clientPath, serverPath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 下载到客户端
     */
    public InputStream getFileStream(String path) throws IOException {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        // HDFS路径
        Path hdfsPath = new Path(path);
        return fileSystem.open(hdfsPath);
    }

    @Override
    public void copyFile(String sourcePath, String targetPath) {
        if (StringUtils.isEmpty(sourcePath) || StringUtils.isEmpty(targetPath)) {
            return;
        }
        // 原始文件路径
        Path oldPath = new Path(sourcePath);
        // 目标路径
        Path newPath = new Path(targetPath);

        FSDataInputStream inputStream = null;
        FSDataOutputStream outputStream = null;
        try {
            try {
                inputStream = fileSystem.open(oldPath);
                outputStream = fileSystem.create(newPath);

                IOUtils.copyBytes(inputStream, outputStream, bufferSize, false);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        }

    }

    @Override
    public byte[] openFileToBytes(String path) {
        byte[] bytes = null;
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        // 目标路径
        Path srcPath = new Path(path);
        try {
            FSDataInputStream inputStream = fileSystem.open(srcPath);
            bytes = IOUtils.readFullyToByteArray(inputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return bytes;

    }

    @Override
    public BlockLocation[] getFileBlockLocations(String path) {
        BlockLocation[] blocks = null;
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        // 目标路径
        Path srcPath = new Path(path);
        try {
            FileStatus fileStatus = fileSystem.getFileStatus(srcPath);
            blocks = fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return blocks;
    }
}
