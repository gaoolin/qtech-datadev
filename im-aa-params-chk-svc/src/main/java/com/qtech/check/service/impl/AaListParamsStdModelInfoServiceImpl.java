package com.qtech.check.service.impl;

import com.qtech.check.mapper.AaListParamsStdModelInfoMapper;
import com.qtech.check.pojo.AaListParamsStdModelInfo;
import com.qtech.check.service.IAaListParamsStdModelInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/07/22 14:34:36
 * desc   :
 */

@Service
public class AaListParamsStdModelInfoServiceImpl implements IAaListParamsStdModelInfoService {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsStdModelInfoServiceImpl.class);

    @Autowired
    private AaListParamsStdModelInfoMapper aaListParamsStdModelInfoMapper;

    @Override
    public List<AaListParamsStdModelInfo> selectAaListParamsStdModelInfoList(AaListParamsStdModelInfo aaListParamsStdModelInfo) {
        try {
            return aaListParamsStdModelInfoMapper.selectAaListParamsStdModelInfoList(aaListParamsStdModelInfo);
        } catch (Exception e) {
            logger.error("selectAaListParamsStdModelInfoList error", e);
        }
        return null;
    }

    @Override
    public AaListParamsStdModelInfo selectOneAaListParamsStdModelInfo(AaListParamsStdModelInfo aaListParamsStdModelInfo) {
        List<AaListParamsStdModelInfo> list = null;
        try {
            list = aaListParamsStdModelInfoMapper.selectAaListParamsStdModelInfoList(aaListParamsStdModelInfo);
        } catch (Exception e) {
            logger.error("selectOneAaListParamsStdModelInfo error", e);
        }

        if (CollectionUtils.isNotEmpty(list)) {
            int size = list.size();
            if (size > 1) {
                throw new TooManyResultsException(String.format("Expected one result (or null) to be returned by selectOne(), but found: %s", size));
            }
            return list.get(0);
        }
        return null;
    }
}
