package com.qtech.check.service;

import com.qtech.check.pojo.EqReverseCtrlInfo;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/22 10:43:40
 * desc   :
 */


public interface IAaListParamsCheckResultService<T extends EqReverseCtrlInfo> {
    public boolean save(T entity);
}
