package com.qtech.service.service.database.impl;

import com.qtech.service.entity.database.ImSparkJobInfo;
import com.qtech.service.mapper.database.OracleMapper;
import com.qtech.service.service.database.IOracleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2025/01/03 14:38:29
 * desc   :
 */

@Service
public class OracleServiceImpl implements IOracleService {
    /**
     * @param jobName
     * @return
     */

    @Autowired
    private OracleMapper oracleMapper;

    @Override
    public ImSparkJobInfo getSparkJobInfo(String jobName) {
        return oracleMapper.getSparkJobInfo(jobName);
    }

    /**
     * @param imSparkJobInfo
     * @return
     */
    @Override
    public boolean updateSparkJobInfo(ImSparkJobInfo imSparkJobInfo) {
        return oracleMapper.updateSparkJobInfo(imSparkJobInfo) > 0;
    }

    /**
     * @param jobName
     * @return
     */
    @Override
    public String getSparkJobSql(String jobName) {
        return oracleMapper.getSparkJobSql(jobName);
    }
}
