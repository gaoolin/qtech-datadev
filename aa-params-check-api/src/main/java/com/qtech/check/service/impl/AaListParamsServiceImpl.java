package com.qtech.check.service.impl;

import com.qtech.check.pojo.AaListParams;
import com.qtech.check.config.dynamic.DataSourceNames;
import com.qtech.check.config.dynamic.DataSourceSwitch;
import com.qtech.check.mapper.AaListParamsMapper;
import com.qtech.check.service.IAaListParamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 16:47:16
 * desc   :
 */

@Slf4j
@Service
public class AaListParamsServiceImpl implements IAaListParamsService {

    @Autowired
    private AaListParamsMapper aaListParamsMapper;

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public void insertAaListParams(AaListParams aaListParams) {
        try {
            aaListParamsMapper.insertAaListParams(aaListParams);
        } catch (Exception e) {
            log.error("insertAaListParams error: {}", e.getMessage());
        }
    }
}
