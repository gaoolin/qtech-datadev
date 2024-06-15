package com.qtech.rabbitmq.service.impl;

import com.qtech.rabbitmq.domain.WbOlpCheckResult;
import com.qtech.rabbitmq.mapper.WbOlpCheckResultMapper;
import com.qtech.rabbitmq.service.IWbOlpCheckResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 17:01:32
 * desc   :
 */

@Slf4j
@Service
public class WbOlpCheckResultServiceImpl implements IWbOlpCheckResultService {

    @Autowired
    private WbOlpCheckResultMapper wbOlpCheckResultMapper;

    @Override
    public int add(WbOlpCheckResult wbOlpCheckResult) {
        try {
            return wbOlpCheckResultMapper.add(wbOlpCheckResult);
        } catch (Exception e) {
            log.error("add wbOlpCheckResult error", e);
        }
        return 0;
    }

    @Override
    public void addBatch(List<WbOlpCheckResult> wbOlpCheckResults) {
        try {
            wbOlpCheckResultMapper.addBatch(wbOlpCheckResults);
        } catch (Exception e) {
            log.error("addBatch wbOlpCheckResult error", e);
        }
    }
}
