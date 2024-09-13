package com.qtech.check.service.impl;

import com.qtech.check.config.dynamic.DataSourceNames;
import com.qtech.check.config.dynamic.DataSourceSwitch;
import com.qtech.check.mapper.AaListParamsCheckResultMapper;
import com.qtech.check.pojo.AaListParamsCheckResult;
import com.qtech.check.service.IAaListParamsCheckResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/22 10:46:24
 * desc   :
 */

@Service
public class AaListParamsCheckResultServiceImpl implements IAaListParamsCheckResultService {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsCheckResultServiceImpl.class);

    @Autowired
    private AaListParamsCheckResultMapper aaListParamsCheckResultMapper;

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int save(AaListParamsCheckResult aaListParamsCheckResult) {
        int i = 0;
        int j = 0;
        try {
            i = aaListParamsCheckResultMapper.insertAaListParamsLatestCheckResult(aaListParamsCheckResult);
            j = aaListParamsCheckResultMapper.insertAaListParamsCheckResult(aaListParamsCheckResult);
            logger.info(">>>>> save aaListParamsCheckResult success:{}", aaListParamsCheckResult);
        } catch (Exception e) {
            logger.error(">>>>> save aaListParamsCheckResult error:{}", e.getMessage());
        }
        return i & j;
    }
}