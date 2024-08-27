package com.qtech.mq.service.impl;

import com.qtech.mq.common.dynamic.DataSourceNames;
import com.qtech.mq.common.dynamic.DataSourceSwitch;
import com.qtech.mq.domain.AaListParams;
import com.qtech.mq.mapper.AaListParamsParsedMapper;
import com.qtech.mq.service.IAaListParamsParsedService;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/16 14:03:59
 * desc   :
 */

@Service
public class AaListParamsParsedServiceImpl implements IAaListParamsParsedService {

    private final AaListParamsParsedMapper aaListParamsParsedMapper;

    public AaListParamsParsedServiceImpl(AaListParamsParsedMapper aaListParamsParsedMapper) {
        this.aaListParamsParsedMapper = aaListParamsParsedMapper;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int save(AaListParams aaListParams) {
        return aaListParamsParsedMapper.save(aaListParams);
    }
}
