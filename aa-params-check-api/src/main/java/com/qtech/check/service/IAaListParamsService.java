package com.qtech.check.service;

import com.qtech.check.pojo.AaListParams;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 16:46:47
 * desc   :
 */


public interface IAaListParamsService {
    List<AaListParams> selectAaListParamsList(AaListParams aaListParams);

    public void insertAaListParams(AaListParams aaListParams);
}
