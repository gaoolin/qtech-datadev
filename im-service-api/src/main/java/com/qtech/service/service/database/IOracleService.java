package com.qtech.service.service.database;

import com.qtech.service.entity.database.ImSparkJobInfo;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2025/01/03 14:38:03
 * desc   :
 */


public interface IOracleService {
    public ImSparkJobInfo getSparkJobInfo(String jobName);

    public boolean updateSparkJobInfo(ImSparkJobInfo imSparkJobInfo);

    public String getSparkJobSql(String jobName);
}
