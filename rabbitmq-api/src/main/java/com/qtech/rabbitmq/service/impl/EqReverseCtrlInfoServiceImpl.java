package com.qtech.rabbitmq.service.impl;


import com.qtech.rabbitmq.common.dynamic.DataSourceNames;
import com.qtech.rabbitmq.common.dynamic.DataSourceSwitch;
import com.qtech.rabbitmq.domain.EqReverseCtrlInfo;
import com.qtech.rabbitmq.mapper.EqReverseCtrlInfoMapper;
import com.qtech.rabbitmq.service.IEqReverseCtrlInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:28:21
 * desc   :
 */

@Slf4j
@Service
public class EqReverseCtrlInfoServiceImpl implements IEqReverseCtrlInfoService {

    private final EqReverseCtrlInfoMapper eqReverseCtrlInfoMapper;

    public EqReverseCtrlInfoServiceImpl(EqReverseCtrlInfoMapper eqReverseCtrlInfoMapper) {
        this.eqReverseCtrlInfoMapper = eqReverseCtrlInfoMapper;
    }

    @Override
    public int upsertOracle(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        try {
            return eqReverseCtrlInfoMapper.upsertOracle(eqReverseCtrlInfoList);
        } catch (Exception e) {
            log.error("EqReverseCtrlInfoServiceImpl.upsertOracle error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int upsertDoris(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        try {
            return eqReverseCtrlInfoMapper.upsertDoris(eqReverseCtrlInfoList);
        } catch (Exception e) {
            log.error("EqReverseCtrlInfoServiceImpl.upsertDoris error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int addBatchDoris(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        try {
            return eqReverseCtrlInfoMapper.addBatchDoris(eqReverseCtrlInfoList);
        } catch (Exception e) {
            log.error("EqReverseCtrlInfoServiceImpl.addBatchDoris error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
