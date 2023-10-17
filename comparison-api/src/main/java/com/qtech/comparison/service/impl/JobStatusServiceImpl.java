package com.qtech.comparison.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.qtech.comparison.entity.JobStatus;
import com.qtech.comparison.mapper.JobStatusMapper;
import com.qtech.comparison.service.IJobStatusService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JobStatusServiceImpl implements IJobStatusService {

    @Resource
    private RedisTemplate<String, String> stringStringRedisTemplate;

    @Autowired
    JobStatusMapper jobStatusMapper;

    @Override
    public String getJobRunDt(String jobName) {
        String jobRunDt = stringStringRedisTemplate.opsForValue().get(REDIS_JOB_RUN_DT_KEY_PREFIX + jobName);
        if (StringUtils.isEmpty(jobRunDt)) {
            JobStatus dbJobRunDt = jobStatusMapper.getDbJobRunDt(jobName);
            if (dbJobRunDt != null) {
                jobRunDt = dbJobRunDt.getPreRunTime();
                stringStringRedisTemplate.opsForValue().set(REDIS_JOB_RUN_DT_KEY_PREFIX + jobName, jobRunDt);
            } else {
                return null;
            }
        }
        return jobRunDt;
    }

    @Override
    public Integer updateJobRunDt(String jobName, String jobRunDt) {
        try {
            stringStringRedisTemplate.opsForValue().set(REDIS_JOB_RUN_DT_KEY_PREFIX + jobName, jobRunDt);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public String getJobRunStat(String jobName) {
        String jobRunStat = stringStringRedisTemplate.opsForValue().get(REDIS_JOB_RUN_STAT_KEY_PREFIX + jobName);
        if (StringUtils.isEmpty(jobRunStat)) {
            JobStatus dbJobRunStat = jobStatusMapper.getDbJobRunStat(jobName);
            if (dbJobRunStat == null) {
                log.error("查询数据库返回空值，jobName：{}", jobName);
            } else {
                jobRunStat = dbJobRunStat.getStatus();
                if (!StringUtils.isEmpty(jobRunStat)) {
                    stringStringRedisTemplate.opsForValue().set(REDIS_JOB_RUN_STAT_KEY_PREFIX + jobName, jobRunStat);
                } else {
                    log.error("查询数据库返回空值，jobName：{}", jobRunStat);
                }
            }
        }
        return jobRunStat;
    }

    @Override
    public Integer updateJobRunStat(String jobName, String jobRunStat) {

        try {
            stringStringRedisTemplate.opsForValue().set(REDIS_JOB_RUN_STAT_KEY_PREFIX + jobName, jobRunStat);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
}
