package com.qtech.olp.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.qtech.olp.entity.WbOlpCheckResult;
import com.qtech.olp.mapper.WbOlpCheckMapper;
import com.qtech.olp.service.IWbOlpCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.qtech.olp.utils.Constants.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:51:45
 * desc   :
 */

@Service
@Slf4j
public class WbOlpCheckServiceImpl implements IWbOlpCheckService {

    private static final int REDIS_EXPIRE_TIME = 1; // 过期时间单位：天
    private static final String REDIS_KEY_PREFIX = WB_OLP_CHECK_REDIS_KEY_PREFIX; // 避免直接在字符串中拼接

    @Resource
    private RedisTemplate<String, String> stringStringRedisTemplate;

    @Autowired
    @Qualifier("wbOlpCheckMapper")
    private WbOlpCheckMapper wbOlpCheckMapper;

    @Override
    public String getOlpCheckResult(String simId) {
        // 校验simId合法性，避免潜在的安全问题
        if (simId == null || simId.trim().isEmpty()) {
            log.error("无效的simId。");
            return "";
        }

        String olpCheckResult = stringStringRedisTemplate.opsForValue().get(REDIS_KEY_PREFIX + simId);

        if (StringUtils.isEmpty(olpCheckResult)) {
            try {
                WbOlpCheckResult wbOlpCheckResultDao = wbOlpCheckMapper.getOlpCheckResult(simId);
                if (wbOlpCheckResultDao != null) {
                    String codeStr = wbOlpCheckResultDao.getCode();
                    String descStr = wbOlpCheckResultDao.getDescription();

                    if (!StringUtils.isEmpty(codeStr) && !StringUtils.isEmpty(descStr)) {
                        StringBuilder result = new StringBuilder(codeStr).append("*").append(descStr);
                        stringStringRedisTemplate.opsForValue().set(REDIS_KEY_PREFIX + simId, result.toString(), REDIS_EXPIRE_TIME, TimeUnit.DAYS);
                        olpCheckResult = result.toString();
                    } else {
                        log.error("查询数据返回空值，simId：{}.", simId);
                    }
                } else {
                    log.error("查询数据返回空值，simId：{}.", simId);
                }
            } catch (Exception e) {
                log.error("查询过程中发生异常，simId：{}，异常：{}", simId, e.getMessage());
                // 根据实际情况，可以返回一个特定的错误码或错误信息
            }
        }

        return olpCheckResult;
    }
}
