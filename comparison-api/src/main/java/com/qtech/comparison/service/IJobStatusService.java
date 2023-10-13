package com.qtech.comparison.service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/11 10:32:25
 * desc   :
 */


public interface IJobStatusService {

    String getJobRunDt(String jobName);

    String getJobRunStat(String jobName);

    Integer updateJobRunDt(String jobName, String jobRunDt);

    Integer updateJobRunStat(String jobName, String runDt);

}
