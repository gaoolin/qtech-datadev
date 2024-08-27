package com.qtech.mq.service.impl;

import com.qtech.mq.common.dynamic.DataSourceNames;
import com.qtech.mq.common.dynamic.DataSourceSwitch;
import com.qtech.mq.domain.EqReverseCtrlInfo;
import com.qtech.mq.mapper.EqReverseCtrlInfoMapper;
import com.qtech.mq.service.IEqReverseCtrlInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:28:21
 * desc   :
 */

@Service
public class EqReverseCtrlInfoServiceImpl implements IEqReverseCtrlInfoService {
    private static final Logger logger = LoggerFactory.getLogger(EqReverseCtrlInfoServiceImpl.class);
    private final EqReverseCtrlInfoMapper eqReverseCtrlInfoMapper;

    @Autowired
    public EqReverseCtrlInfoServiceImpl(EqReverseCtrlInfoMapper eqReverseCtrlInfoMapper) {
        this.eqReverseCtrlInfoMapper = eqReverseCtrlInfoMapper;
    }

    @DataSourceSwitch(name = DataSourceNames.FIRST)
    @Override
    public int upsertOracle(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        if (eqReverseCtrlInfoList != null && !eqReverseCtrlInfoList.isEmpty()) {
            try {
                List<EqReverseCtrlInfo> distinctList = eqReverseCtrlInfoList.stream().distinct().collect(Collectors.toList());
                logger.info(">>>>> eqReverseCtrlInfoList size: {}, distinctList size: {}", eqReverseCtrlInfoList.size(), distinctList.size());
                eqReverseCtrlInfoMapper.upsertOracle(distinctList);
                return distinctList.size();
            } catch (Exception e) {
                logger.error("EqReverseCtrlInfoServiceImpl.upsertOracle error: {}", e.getMessage());
            }
        }
        return 0;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int upsertDoris(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        try {
            eqReverseCtrlInfoMapper.upsertDoris(eqReverseCtrlInfoList);
            return eqReverseCtrlInfoList.size();
        } catch (Exception e) {
            logger.error(">>>>> EqReverseCtrlInfoServiceImpl.upsertDoris error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int addAaListBatchDoris(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        try {
            return eqReverseCtrlInfoMapper.addAaListBatchDoris(eqReverseCtrlInfoList);
        } catch (Exception e) {
            logger.error("EqReverseCtrlInfoServiceImpl.addBatchDoris error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int addWbOlpChkBatchDoris(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        try {
            return eqReverseCtrlInfoMapper.addWbOlpChkBatchDoris(eqReverseCtrlInfoList);
        } catch (Exception e) {
            logger.error("EqReverseCtrlInfoServiceImpl.addBatchDoris error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DataSourceSwitch(name = DataSourceNames.FIRST)
    @Async
    @Override
    public CompletableFuture<Integer> upsertOracleAsync(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        if (eqReverseCtrlInfoList != null && !eqReverseCtrlInfoList.isEmpty()) {
            try {
                // List<EqReverseCtrlInfo> distinctList = eqReverseCtrlInfoList.stream().distinct().collect(Collectors.toList());
                // logger.info(">>>>> eqReverseCtrlInfoList size: {}, distinctList size: {}", eqReverseCtrlInfoList.size(), distinctList.size());
                eqReverseCtrlInfoMapper.upsertOracle(eqReverseCtrlInfoList);
                future.complete(eqReverseCtrlInfoList.size());
            } catch (Exception e) {
                logger.error("EqReverseCtrlInfoServiceImpl.upsertOracle error: {}", e.getMessage());
                future.completeExceptionally(e);
            }
        } else {
            future.complete(0);
        }
        return future;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Async
    @Override
    public CompletableFuture<Integer> upsertDorisAsync(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        try {
            eqReverseCtrlInfoMapper.upsertDoris(eqReverseCtrlInfoList);
            future.complete(eqReverseCtrlInfoList.size());
        } catch (Exception e) {
            logger.error(">>>>> EqReverseCtrlInfoServiceImpl.upsertDoris error: {}", e.getMessage());
            future.completeExceptionally(e);
        }
        return future;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Async
    @Override
    public CompletableFuture<Integer> addAaListBatchDorisAsync(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        try {
            int result = eqReverseCtrlInfoMapper.addAaListBatchDoris(eqReverseCtrlInfoList);
            future.complete(result);
        } catch (Exception e) {
            logger.error("EqReverseCtrlInfoServiceImpl.addAaListBatchDoris error: {}", e.getMessage());
            future.completeExceptionally(e);
        }
        return future;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Async
    @Override
    public CompletableFuture<Integer> addWbOlpChkBatchDorisAsync(List<EqReverseCtrlInfo> eqReverseCtrlInfoList) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        try {
            int result = eqReverseCtrlInfoMapper.addWbOlpChkBatchDoris(eqReverseCtrlInfoList);
            future.complete(result);
        } catch (Exception e) {
            logger.error("EqReverseCtrlInfoServiceImpl.addWbOlpChkBatchDoris error: {}", e.getMessage());
            future.completeExceptionally(e);
        }
        return future;
    }
}
