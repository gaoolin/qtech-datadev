package com.qtech.check.service.impl;

import com.qtech.check.mapper.AaListParamsReverseCtrlInfoMapper;
import com.qtech.check.pojo.AaListParamsCheckResult;
import com.qtech.check.mapper.AaListParamsCheckResultMapper;
import com.qtech.check.service.IAaListParamsCheckResultService;
import com.qtech.check.config.dynamic.DataSourceNames;
import com.qtech.check.config.dynamic.DataSourceSwitch;
import com.qtech.check.service.IAaListParamsReverseCtrlInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/22 10:46:24
 * desc   :
 */

@Slf4j
@Service
public class AaListParamsCheckResultServiceImpl implements IAaListParamsCheckResultService {

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
            log.info(">>>>> save aaListParamsCheckResult success:{}", aaListParamsCheckResult);
        } catch (Exception e) {
            log.error(">>>>> save aaListParamsCheckResult error:{}", e.getMessage());
        }
        return i & j;
    }
}