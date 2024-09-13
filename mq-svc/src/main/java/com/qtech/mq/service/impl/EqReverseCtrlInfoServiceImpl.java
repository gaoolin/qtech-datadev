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

import java.util.concurrent.CompletableFuture;

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
    public int upsertOracle(EqReverseCtrlInfo eqReverseCtrlInfo) {
        if (eqReverseCtrlInfo != null) {
            try {
                return eqReverseCtrlInfoMapper.upsertOracle(eqReverseCtrlInfo);
            } catch (Exception e) {
                logger.error("EqReverseCtrlInfoServiceImpl.upsertOracle error: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int upsertDoris(EqReverseCtrlInfo eqReverseCtrlInfo) {
        try {
            return eqReverseCtrlInfoMapper.upsertDoris(eqReverseCtrlInfo);
        } catch (Exception e) {
            logger.error(">>>>> EqReverseCtrlInfoServiceImpl.upsertDoris error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int addAaListDoris(EqReverseCtrlInfo eqReverseCtrlInfo) {
        try {
            return eqReverseCtrlInfoMapper.addAaListDoris(eqReverseCtrlInfo);
        } catch (Exception e) {
            logger.error("EqReverseCtrlInfoServiceImpl.addBatchDoris error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public int addWbOlpChkDoris(EqReverseCtrlInfo eqReverseCtrlInfo) {
        try {
            return eqReverseCtrlInfoMapper.addWbOlpChkDoris(eqReverseCtrlInfo);
        } catch (Exception e) {
            logger.error("EqReverseCtrlInfoServiceImpl.addBatchDoris error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DataSourceSwitch(name = DataSourceNames.FIRST)
    @Async
    @Override
    public CompletableFuture<Integer> upsertOracleAsync(EqReverseCtrlInfo eqReverseCtrlInfo) {
        if (eqReverseCtrlInfo != null) {
            try {
                eqReverseCtrlInfoMapper.upsertOracle(eqReverseCtrlInfo);
                return CompletableFuture.completedFuture(1);
            } catch (Exception e) {
                logger.error("EqReverseCtrlInfoServiceImpl.upsertOracleAsync error: {}", e.getMessage());
                return CompletableFuture.completedFuture(0);
            }
        }
        return CompletableFuture.completedFuture(0);
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Async
    @Override
    public CompletableFuture<Integer> upsertDorisAsync(EqReverseCtrlInfo eqReverseCtrlInfo) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        try {
            eqReverseCtrlInfoMapper.upsertDoris(eqReverseCtrlInfo);
            future.complete(1);
        } catch (Exception e) {
            logger.error(">>>>> EqReverseCtrlInfoServiceImpl.upsertDoris error: {}", e.getMessage());
            future.completeExceptionally(e);
        }
        return future;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Async
    @Override
    public CompletableFuture<Integer> addAaListDorisAsync(EqReverseCtrlInfo eqReverseCtrlInfo) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        try {
            int result = eqReverseCtrlInfoMapper.addAaListDoris(eqReverseCtrlInfo);
            future.complete(1);
        } catch (Exception e) {
            logger.error("EqReverseCtrlInfoServiceImpl.addAaListBatchDoris error: {}", e.getMessage());
            future.completeExceptionally(e);
        }
        return future;
    }

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Async
    @Override
    public CompletableFuture<Integer> addWbOlpChkDorisAsync(EqReverseCtrlInfo eqReverseCtrlInfo) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        try {
            int result = eqReverseCtrlInfoMapper.addWbOlpChkDoris(eqReverseCtrlInfo);
            future.complete(1);
        } catch (Exception e) {
            logger.error("EqReverseCtrlInfoServiceImpl.addWbOlpChkBatchDoris error: {}", e.getMessage());
            future.completeExceptionally(e);
        }
        return future;
    }
}
