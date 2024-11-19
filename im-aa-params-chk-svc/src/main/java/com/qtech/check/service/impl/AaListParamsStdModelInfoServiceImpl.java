package com.qtech.check.service.impl;

import com.qtech.check.exception.DataAccessException;
import com.qtech.check.mapper.AaListParamsStdModelInfoMapper;
import com.qtech.check.pojo.AaListParamsStdModelInfo;
import com.qtech.check.service.IAaListParamsStdModelInfoService;
import com.qtech.common.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        Objects.requireNonNull(aaListParamsStdModelInfo, ">>>>> 标准模版信息不能为空！");

        String prodType = aaListParamsStdModelInfo.getProdType();
        if (StringUtils.isBlank(prodType)) {
            logger.error(">>>>> 机型信息不能为空！");
            return null;
        }

        try {
            List<AaListParamsStdModelInfo> list = aaListParamsStdModelInfoMapper.selectAaListParamsStdModelInfoList(aaListParamsStdModelInfo);
            if (CollectionUtils.isNotEmpty(list)) {
                int size = list.size();
                if (size > 1) {
                    logger.error("Expected one result (or null) to be returned by selectOne(), but found: {}", size);
                    throw new TooManyResultsException(String.format("Expected one result (or null) to be returned by selectOne(), but found: %s", size));
                }
                return list.get(0);
            }
        } catch (Exception e) {
            logger.error("selectOneAaListParamsStdModelInfo error for prodType: {}", prodType, e);
            throw new DataAccessException("Error occurred while selecting AaListParamsStdModelInfo", e);
        }

        return null;
    }

}
