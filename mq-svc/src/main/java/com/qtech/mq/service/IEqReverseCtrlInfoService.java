package com.qtech.mq.service;


import com.qtech.mq.common.dynamic.DataSourceNames;
import com.qtech.mq.common.dynamic.DataSourceSwitch;
import com.qtech.mq.domain.EqReverseCtrlInfo;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:27:36
 * desc   :
 */


public interface IEqReverseCtrlInfoService {

    int upsertOracle(EqReverseCtrlInfo eqReverseCtrlInfo);

    int upsertDoris(EqReverseCtrlInfo eqReverseCtrlInfo);

    int addAaListDoris(EqReverseCtrlInfo eqReverseCtrlInfo);

    int addWbOlpChkDoris(EqReverseCtrlInfo eqReverseCtrlInfo);

    int upsertPostgres(EqReverseCtrlInfo eqReverseCtrlInfo);

    CompletableFuture<Integer> upsertOracleAsync(EqReverseCtrlInfo eqReverseCtrlInfo);

    CompletableFuture<Integer> upsertDorisAsync(EqReverseCtrlInfo eqReverseCtrlInfo);

    CompletableFuture<Integer> addAaListDorisAsync(EqReverseCtrlInfo eqReverseCtrlInfo);

    CompletableFuture<Integer> addWbOlpChkDorisAsync(EqReverseCtrlInfo eqReverseCtrlInfo);

    CompletableFuture<Integer> upsertPostgresAsync(EqReverseCtrlInfo eqReverseCtrlInfo);
}
