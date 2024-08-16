package com.qtech.rabbitmq.service.impl;

import com.qtech.rabbitmq.common.dynamic.DataSourceNames;
import com.qtech.rabbitmq.common.dynamic.DataSourceSwitch;
import com.qtech.rabbitmq.domain.AaListParams;
import com.qtech.rabbitmq.mapper.AaListParamsParsedMapper;
import com.qtech.rabbitmq.service.IAaListParamsParsedService;
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
