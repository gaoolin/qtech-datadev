package com.qtech.ceph.hdfs.service;

import java.io.IOException;
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/02 10:41:22
 * desc   :
 */


public interface IHdfsFileMergerService {
    void mergeFiles(List<String> sourcePaths, String destPath) throws IOException;
}
