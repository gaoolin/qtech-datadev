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

    int upsertOracle(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);

    int upsertDoris(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);

    int addAaListBatchDoris(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);

    int addWbOlpChkBatchDoris(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);

    CompletableFuture<Integer> upsertOracleAsync(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);

    CompletableFuture<Integer> upsertDorisAsync(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);

    CompletableFuture<Integer> addAaListBatchDorisAsync(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);

    CompletableFuture<Integer> addWbOlpChkBatchDorisAsync(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);
}
