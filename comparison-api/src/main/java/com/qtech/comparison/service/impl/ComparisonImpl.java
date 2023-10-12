package com.qtech.comparison.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.qtech.comparison.entity.ComparisonResult;
import com.qtech.comparison.mapper.ComparisonMapper;
import com.qtech.comparison.service.IComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.qtech.comparison.utils.Constants.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:51:45
 * desc   :
 */

@DS("db1")
@Service
public class ComparisonImpl implements IComparisonService {

    @Resource
    private RedisTemplate<String, String> stringStringRedisTemplate;

    @Autowired
    ComparisonMapper comparisonMapper;

    @Override
    public String getComparisonResult(String programName, String simId) {
        String comparisonResult;
        comparisonResult = stringStringRedisTemplate.opsForValue().get(WB_COMPARISON_REDIS_KEY_PREFIX + simId);

        if (StringUtils.isEmpty(comparisonResult)) {
            ComparisonResult comparisonResultDao = comparisonMapper.getComparisonResult(simId);

            String codeStr = StringUtils.isEmpty(comparisonResultDao.getCode()) ? "/" : comparisonResultDao.getCode();
            String descStr = StringUtils.isEmpty(comparisonResultDao.getCode()) ? "/" : comparisonResultDao.getDescription();
            stringStringRedisTemplate.opsForValue().set(WB_COMPARISON_REDIS_KEY_PREFIX + simId, codeStr + "*" + descStr, 15, TimeUnit.DAYS);
        }
        return comparisonResult;
    }

    @Override
    public Integer addComparisonResult(String redisKey, String redisVal, Integer days) {

        try {
            stringStringRedisTemplate.opsForValue().set(WB_COMPARISON_REDIS_KEY_PREFIX + redisKey, redisVal, days, TimeUnit.DAYS);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public String getComparisonJobStatus() {
        return stringStringRedisTemplate.opsForValue().get(WB_COMPARISON_REDIS_JOB_STAT_KEY_PREFIX + "status");
    }

    @Override
    public Integer updateJobStatus(String redisKey, String redisVal) {

        try {
            stringStringRedisTemplate.opsForValue().set(WB_COMPARISON_REDIS_JOB_STAT_KEY_PREFIX + redisKey, redisVal);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
}
