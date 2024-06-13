package com.qtech.rabbitmq.service.impl;

import com.qtech.rabbitmq.domain.WbOlpCheckResult;
import com.qtech.rabbitmq.mapper.WbOlpCheckResultMapper;
import com.qtech.rabbitmq.service.IWbOlpCheckResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 17:01:32
 * desc   :
 */

@Service
public class WbOlpCheckResultServiceImpl implements IWbOlpCheckResultService {

    @Autowired
    private WbOlpCheckResultMapper wbOlpCheckResultMapper;
    @Override
    public int add(WbOlpCheckResult wbOlpCheckResult) {
        return wbOlpCheckResultMapper.add(wbOlpCheckResult);
    }
}
