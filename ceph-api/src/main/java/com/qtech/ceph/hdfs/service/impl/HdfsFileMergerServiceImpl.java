package com.qtech.ceph.hdfs.service.impl;

import com.qtech.ceph.hdfs.service.IHdfsFileMergerService;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/02 10:41:06
 * desc   :
 */

@Service
public class HdfsFileMergerServiceImpl implements IHdfsFileMergerService {

    @Autowired
    private FileSystem fileSystem;

    /**
     * 合并HDFS上的多个小文件到一个大文件中。
     *
     * @param sourcePaths 源文件路径列表
     * @param destPath    目标文件路径
     */
    @Override
    public void mergeFiles(List<String> sourcePaths, String destPath) throws IOException {
        FSDataOutputStream outputStream = null;
        try {
            // 创建目标文件的输出流
            Path destFile = new Path(destPath);
            outputStream = fileSystem.create(destFile);

            for (String sourcePath : sourcePaths) {
                // 读取源文件
                Path sourceFile = new Path(sourcePath);
                FSDataInputStream inputStream = fileSystem.open(sourceFile);

                // 将源文件内容写入目标文件
                IOUtils.copyBytes(inputStream, outputStream, fileSystem.getConf(), false);

                // 关闭输入流
                inputStream.close();
            }
        } finally {
            // 确保关闭输出流
            IOUtils.closeStream(outputStream);
        }
    }
}
