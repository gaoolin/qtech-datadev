package com.qtech.olp.service;

import com.qtech.olp.entity.AaListMessage;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 16:46:47
 * desc   :
 */


public interface IAaParamsService {
    List<AaListMessage> selectAaParamsList(AaListMessage aaListMessage);

    public int insertAaParams(AaListMessage aaListMessage);
}
