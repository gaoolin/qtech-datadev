package com.qtech.comparison.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.qtech.comparison.entity.ComparisonResult;
import com.qtech.comparison.mapper.WbComparisonMapper;
import com.qtech.comparison.service.IWbComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static com.qtech.comparison.utils.Constants.WB_COMPARISON_REDIS_KEY_PREFIX;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:51:45
 * desc   :
 */


@Service
public class WbComparisonImpl implements IWbComparisonService {

    @Resource
    private RedisTemplate<String, String> stringStringRedisTemplate;

    @Autowired
    private WbComparisonMapper wbComparisonMapper;

    @Override
    public String getComparisonResult(String simId) {
        String comparisonResult;
        ValueOperations<String, String> ops = stringStringRedisTemplate.opsForValue();
        comparisonResult = ops.get(WB_COMPARISON_REDIS_KEY_PREFIX + simId);

        if (StringUtils.isEmpty(comparisonResult)) {
            ComparisonResult comparisonResultDao = wbComparisonMapper.getComparisonResult(simId);

            String codeStr = StringUtils.isEmpty(comparisonResultDao.getCode()) ? "/" : comparisonResultDao.getCode();
            String descStr = StringUtils.isEmpty(comparisonResultDao.getCode())?"/":comparisonResultDao.getDescription();
            ops.set(WB_COMPARISON_REDIS_KEY_PREFIX + simId, codeStr + "*" + descStr, 15, TimeUnit.DAYS);
        }
        return comparisonResult;
    }
}
