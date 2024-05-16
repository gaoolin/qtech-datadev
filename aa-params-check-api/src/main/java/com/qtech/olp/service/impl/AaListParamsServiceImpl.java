package com.qtech.olp.service.impl;

import com.qtech.olp.config.dynamic.DataSourceNames;
import com.qtech.olp.config.dynamic.DataSourceSwitch;
import com.qtech.olp.entity.AaListParamsMessage;
import com.qtech.olp.mapper.AaListParamsMapper;
import com.qtech.olp.service.IAaListParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 16:47:16
 * desc   :
 */

@Service
public class AaListParamsServiceImpl implements IAaListParamsService {

    @Autowired
    private AaListParamsMapper aaListParamsMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public List<AaListParamsMessage> selectAaListParamsList(AaListParamsMessage aaListParamsMessage) {
        return aaListParamsMapper.selectAaListParamsList(aaListParamsMessage);
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public void insertAaListParams(AaListParamsMessage aaListParamsMessage) {
        aaListParamsMapper.insertAaListParams(aaListParamsMessage);
    }
}
