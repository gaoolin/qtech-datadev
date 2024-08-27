package com.qtech.mq.service;

import com.qtech.mq.common.dynamic.DataSourceNames;
import com.qtech.mq.common.dynamic.DataSourceSwitch;
import com.qtech.mq.domain.EqReverseCtrlInfo;
import com.qtech.mq.domain.WbOlpRawData;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/23 13:38:51
 * desc   :
 */


public interface IWbOlpRawDataService {
    public int addWbOlpRawDataBatch(List<WbOlpRawData> wbOlpRawDataList);

    public CompletableFuture<Integer> addWbOlpRawDataBatchAsync(List<WbOlpRawData> wbOlpRawDataList);
}
