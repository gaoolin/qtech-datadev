package com.qtech.mq.service;

import com.qtech.mq.domain.WbOlpRawData;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/23 13:38:51
 * desc   :
 */


public interface IWbOlpRawDataService {
    public int addWbOlpRawDataBatch(List<WbOlpRawData> wbOlpRawDataList);
}
