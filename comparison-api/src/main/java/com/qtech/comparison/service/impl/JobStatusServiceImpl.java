package com.qtech.comparison.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.qtech.comparison.entity.JobStatus;
import com.qtech.comparison.mapper.JobStatusMapper;
import com.qtech.comparison.service.IJobStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.qtech.comparison.utils.Constants.REDIS_JOB_RUN_DT_KEY_PREFIX;
import static com.qtech.comparison.utils.Constants.REDIS_JOB_RUN_STAT_KEY_PREFIX;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/11 10:32:55
 * desc   :
 */

@DS("db2")
@Service
public class JobStatusServiceImpl implements IJobStatusService {

    @Resource
    private RedisTemplate<String, String> stringStringRedisTemplate;

    @Autowired
    JobStatusMapper jobStatusMapper;

    @Override
    public String getJobRunDt(String programName) {
        String jobRunDt = stringStringRedisTemplate.opsForValue().get(REDIS_JOB_RUN_DT_KEY_PREFIX + programName);
        if (StringUtils.isEmpty(jobRunDt)) {
            JobStatus dbJobRunDt = jobStatusMapper.getDbJobRunDt(programName);
            if (dbJobRunDt != null) {
                jobRunDt = dbJobRunDt.getPreRunTime();
                stringStringRedisTemplate.opsForValue().set(REDIS_JOB_RUN_DT_KEY_PREFIX + programName, jobRunDt);
            } else {
                return null;
            }
        }
        return jobRunDt;
    }

    @Override
    public Integer updateJobRunDt(String programName, String jobRunDt) {
        try {
            stringStringRedisTemplate.opsForValue().set(REDIS_JOB_RUN_DT_KEY_PREFIX + programName, jobRunDt);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public String getJobRunStat(String programName) {
        String jobRunStat = stringStringRedisTemplate.opsForValue().get(REDIS_JOB_RUN_STAT_KEY_PREFIX + programName);
        if (StringUtils.isEmpty(jobRunStat)) {
            jobRunStat = jobStatusMapper.getDbJobRunStat(programName).getStatus();
            stringStringRedisTemplate.opsForValue().set(REDIS_JOB_RUN_STAT_KEY_PREFIX + programName, jobRunStat);
        }
        return jobRunStat;
    }

    @Override
    public Integer updateJobRunStat(String programName, String jobRunStat) {

        try {
            stringStringRedisTemplate.opsForValue().set(REDIS_JOB_RUN_STAT_KEY_PREFIX + programName, jobRunStat);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
}
