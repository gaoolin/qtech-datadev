package com.qtech.mq.service.impl;

import com.qtech.mq.common.dynamic.DataSourceNames;
import com.qtech.mq.common.dynamic.DataSourceSwitch;
import com.qtech.mq.domain.WbOlpRawData;
import com.qtech.mq.mapper.WbOlpRawDataMapper;
import com.qtech.mq.service.IWbOlpRawDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/23 13:39:12
 * desc   :
 */

@Service
public class WbOlpRawDataServiceImpl implements IWbOlpRawDataService {
    private static WbOlpRawDataMapper wbOlpRawDataMapper;

    public WbOlpRawDataServiceImpl(WbOlpRawDataMapper wbOlpRawDataMapper) {
        WbOlpRawDataServiceImpl.wbOlpRawDataMapper = wbOlpRawDataMapper;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int addWbOlpRawDataBatch(List<WbOlpRawData> wbOlpRawDataList) {
        if (wbOlpRawDataList == null || wbOlpRawDataList.isEmpty()) {
            return 0;
        }
        return wbOlpRawDataMapper.addWbOLpRawDataBatch(wbOlpRawDataList);
    }
}
