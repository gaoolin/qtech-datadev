package com.qtech.service.service.chk;

import com.qtech.service.entity.EqReverseCtrlInfo;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:27:36
 * desc   :
 */


public interface IEqReverseCtrlService {
    public List<EqReverseCtrlInfo> selectEqReverseCtrlInfo(EqReverseCtrlInfo eqReverseCtrlInfo);

    public EqReverseCtrlInfo selectOneEqReverseCtrlInfo(EqReverseCtrlInfo eqReverseCtrlInfo);

    public EqReverseCtrlInfo selectEqReverseCtrlInfoBySimId(String simId);
}
