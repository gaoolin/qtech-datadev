package com.qtech.check.service.impl;

import com.qtech.check.pojo.AaListParamsCheckResult;
import com.qtech.check.mapper.AaListParamsCheckResultMapper;
import com.qtech.check.service.IAaListParamsCheckResultService;
import com.qtech.check.config.dynamic.DataSourceNames;
import com.qtech.check.config.dynamic.DataSourceSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/22 10:46:24
 * desc   :
 */

@Service
public class AaListParamsCheckResultServiceImpl implements IAaListParamsCheckResultService {

    @Autowired
    private AaListParamsCheckResultMapper aaListParamsCheckResultMapper;

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int save(AaListParamsCheckResult aaListParamsCheckResult) {
        return aaListParamsCheckResultMapper.insertAaListParamsCheckResult(aaListParamsCheckResult);
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int update(AaListParamsCheckResult aaListParamsCheckResult) {
        return 0;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int delete(AaListParamsCheckResult aaListParamsCheckResult) {
        return 0;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public List<AaListParamsCheckResult> selectAaListParamsCheckResultList(AaListParamsCheckResult aaListParamsCheckResult) {
        return aaListParamsCheckResultMapper.selectAaListParamsCheckResultList(aaListParamsCheckResult);
    }
}
