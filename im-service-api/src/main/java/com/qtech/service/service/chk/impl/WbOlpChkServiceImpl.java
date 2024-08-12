package com.qtech.service.service.chk.impl;

import com.alibaba.druid.util.StringUtils;
import com.qtech.service.entity.WbOlpChkResult;
import com.qtech.service.mapper.chk.WbOlpChkResultMapper;
import com.qtech.service.service.chk.IWbOlpChkService;
import com.qtech.service.utils.RedisClusterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.qtech.service.common.Constants.WB_OLP_CHECK_REDIS_KEY_PREFIX;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:51:45
 * desc   :
 */

@Service
@Slf4j
public class WbOlpChkServiceImpl implements IWbOlpChkService {

    private static final Long REDIS_EXPIRE_TIME = 60L; // 过期时间单位：分钟
    private static final String REDIS_KEY_PREFIX = WB_OLP_CHECK_REDIS_KEY_PREFIX; // 避免直接在字符串中拼接

    @Autowired
    @Qualifier("wbOlpChkResultMapper")
    private WbOlpChkResultMapper wbOlpCheckMapper;

    @Override
    public String getOlpCheckResult(String simId) {
        // 校验simId合法性，避免潜在的安全问题
        if (simId == null || simId.trim().isEmpty()) {
            log.error("无效的simId。");
            return "";
        }

        String olpCheckResult = RedisClusterUtil.get(REDIS_KEY_PREFIX + simId);

        if (StringUtils.isEmpty(olpCheckResult)) {
            try {
                WbOlpChkResult wbOlpCheckResultDao = wbOlpCheckMapper.getOlpChkResult(simId);
                if (wbOlpCheckResultDao != null) {
                    String codeStr = wbOlpCheckResultDao.getCode();
                    String descStr = wbOlpCheckResultDao.getDescription();

                    if (!StringUtils.isEmpty(codeStr) && !StringUtils.isEmpty(descStr)) {
                        StringBuilder result = new StringBuilder(codeStr).append("*").append(descStr);
                        RedisClusterUtil.setEx(REDIS_KEY_PREFIX + simId, REDIS_EXPIRE_TIME, result.toString());
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
