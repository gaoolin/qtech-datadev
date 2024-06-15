package com.qtech.rabbitmq.service;

import com.qtech.rabbitmq.domain.WbOlpCheckResult;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 17:00:31
 * desc   :
 */


public interface IWbOlpCheckResultService {

    int add(WbOlpCheckResult wbOlpCheckResult);
    void addBatch(List<WbOlpCheckResult> wbOlpCheckResults);
}
