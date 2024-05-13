package com.qtech.olp.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.qtech.olp.entity.ComparisonResult;
import com.qtech.olp.mapper.ComparisonMapper;
import com.qtech.olp.service.IComparisonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.qtech.olp.utils.Constants.WB_COMPARISON_REDIS_JOB_STAT_KEY_PREFIX;
import static com.qtech.olp.utils.Constants.WB_COMPARISON_REDIS_KEY_PREFIX;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:51:45
 * desc   :
 */

@DS("db1")
@Service
@Slf4j
public class ComparisonServiceImpl implements IComparisonService {

    @Resource
    private RedisTemplate<String, String> stringStringRedisTemplate;

    @Autowired
    ComparisonMapper comparisonMapper;

    @Override
    public String getComparisonResult(String programName, String simId) {
        String codeStr = "";
        String descStr = "";
        String comparisonResult = stringStringRedisTemplate.opsForValue().get(WB_COMPARISON_REDIS_KEY_PREFIX + simId);

        if (StringUtils.isEmpty(comparisonResult)) {
            ComparisonResult comparisonResultDao = comparisonMapper.getComparisonResult(simId);
            if (comparisonResultDao == null) {
                log.error("查询数据返回空值，simId：{}.", simId);
            } else {
                codeStr = comparisonResultDao.getCode();
                descStr = comparisonResultDao.getDescription();
                if (!StringUtils.isEmpty(codeStr) && !StringUtils.isEmpty(descStr)) {
                    stringStringRedisTemplate.opsForValue().set(WB_COMPARISON_REDIS_KEY_PREFIX + simId, codeStr + "*" + descStr, 1, TimeUnit.DAYS);
                } else {
                    log.error("查询数据返回空值，simId：{}.", simId);
                }
            }
            comparisonResult = codeStr + "*" + descStr;
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
