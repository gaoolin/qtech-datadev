package com.qtech.check.service;

import com.qtech.check.pojo.AaListParamsCheckResult;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/22 10:43:40
 * desc   :
 */


public interface IAaListParamsCheckResultService {
    public int save(AaListParamsCheckResult aaListParamsCheckResult);

    public int update(AaListParamsCheckResult aaListParamsCheckResult);

    public int delete(AaListParamsCheckResult aaListParamsCheckResult);

    public List<AaListParamsCheckResult> selectAaListParamsCheckResultList(AaListParamsCheckResult aaListParamsCheckResult);
}
