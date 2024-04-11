package com.qtech.rabbitmq.service.impl;

import com.qtech.rabbitmq.domain.WbComparisonResult;
import com.qtech.rabbitmq.mapper.WbComparisonResultMapper;
import com.qtech.rabbitmq.service.IWbComparisonResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 17:01:32
 * desc   :
 */

@Service
public class WbComparisonResultServiceImpl implements IWbComparisonResultService {

    @Autowired
    private WbComparisonResultMapper wbComparisonResultMapper;
    @Override
    public int add(WbComparisonResult wbComparisonResult) {
        return wbComparisonResultMapper.add(wbComparisonResult);
    }
}
