package com.qtech.ceph.hdfs.service;

import org.apache.hadoop.fs.BlockLocation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/02 08:49:40
 * desc   :
 */


/**
 * hadoop hdfs 通用接口定义
 *
 * @author gaozhlin
 */
public interface IHDFSService {

    /**
     * HDFS 文件夹创建
     *
     * @param path
     * @return
     */
    public boolean mkdirFolder(String path);

    /**
     * HDFS 文件是否存在
     *
     * @param path
     * @return
     */
    public boolean existFile(String path);

    /**
     * HDFS 读取目录信息
     *
     * @param path
     * @return
     */
    public List<Map<String, Object>> readCatalog(String path);

    /**
     * HDFS 创建文件
     *
     * @param path
     * @param file
     */
    public void createFile(String path, MultipartFile file);

    /**
     * HDFS 读取文件内容
     *
     * @param path
     * @return
     */
    public String readFileContent(String path);

    /**
     * HDFS 读完文件列表
     *
     * @param path
     * @return
     */
    public List<Map<String, String>> listFile(String path);

    /**
     * HDFS 文件重命名
     *
     * @param oldName
     * @param newName
     * @return
     */
    public boolean renameFile(String oldName, String newName);

    /**
     * HDFS 文件删除
     *
     * @param path
     * @return
     */
    public boolean deleteFile(String path);

    /**
     * HDFS 文件上传
     *
     * @param path
     * @param uploadPath
     */
    public void uploadFile(String path, String uploadPath);

    /**
     * HDFS 文件下载
     *
     * @param path
     * @param downloadPath
     */
    public void downloadFile(String path, String downloadPath);

    /**
     * HDFS 文件下载到客户端
     *
     * @param path
     */
    public InputStream getFileStream(String path) throws IOException;

    /**
     * HDFS 文件复制
     *
     * @param sourcePath
     * @param targetPath
     */
    public void copyFile(String sourcePath, String targetPath);


    /**
     * HDFS 读取指定文件 返回字节数组
     *
     * @param path
     * @return
     */
    public byte[] openFileToBytes(String path);

    /**
     * HDFS 获取指定文件 BlockLocation信息
     *
     * @param path
     * @return
     * @throws Exception
     */
    public BlockLocation[] getFileBlockLocations(String path);

}
