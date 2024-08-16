package com.qtech.rabbitmq.service;


import com.qtech.rabbitmq.domain.EqReverseCtrlInfo;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:27:36
 * desc   :
 */


public interface IEqReverseCtrlInfoService {
    int upsertOracle(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);
    int upsertDoris(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);
    int addBatchDoris(List<EqReverseCtrlInfo> eqReverseCtrlInfoList);
}
