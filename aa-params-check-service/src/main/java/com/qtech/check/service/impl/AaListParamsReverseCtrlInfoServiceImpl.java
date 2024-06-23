package com.qtech.check.service.impl;

import com.qtech.check.config.dynamic.DataSourceNames;
import com.qtech.check.config.dynamic.DataSourceSwitch;
import com.qtech.check.mapper.AaListParamsReverseCtrlInfoMapper;
import com.qtech.check.pojo.AaListParamsCheckResult;
import com.qtech.check.service.IAaListParamsReverseCtrlInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/22 17:41:32
 * desc   :
 */

@Slf4j
@Service
public class AaListParamsReverseCtrlInfoServiceImpl implements IAaListParamsReverseCtrlInfoService {

    @Autowired
    private AaListParamsReverseCtrlInfoMapper aaListParamsReverseCtrlInfoMapper;
    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public void insert(AaListParamsCheckResult aaListParamsCheckResult) {
        try {
            aaListParamsReverseCtrlInfoMapper.insert(aaListParamsCheckResult);
        } catch (Exception e) {
            log.error(">>>>> insert aaListParamsReverseCtrlInfo error:{}", e.getMessage());
        }
    }
}
