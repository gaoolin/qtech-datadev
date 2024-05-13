package com.qtech.olp.service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:46:53
 * desc   :
 */


public interface IComparisonService {

    String getComparisonResult(String programName, String simId);

    Integer addComparisonResult(String redisKey, String redisVal, Integer days);

    Integer updateJobStatus(String redisKey, String redisVal);

    String getComparisonJobStatus();

}
