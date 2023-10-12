package com.qtech.comparison.service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/11 10:32:25
 * desc   :
 */


public interface IJobStatusService {

    String getJobRunDt(String programName);

    String getJobRunStat(String programName);

    Integer updateJobRunDt(String programName, String jobRunDt);

    Integer updateJobRunStat(String programName, String runDt);

}
