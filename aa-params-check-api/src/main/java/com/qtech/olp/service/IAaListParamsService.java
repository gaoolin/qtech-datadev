package com.qtech.olp.service;

import com.qtech.olp.entity.AaListParamsMessage;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 16:46:47
 * desc   :
 */


public interface IAaListParamsService {
    List<AaListParamsMessage> selectAaListParamsList(AaListParamsMessage aaListParamsMessage);

    public void insertAaListParams(AaListParamsMessage aaListParamsMessage);
}
