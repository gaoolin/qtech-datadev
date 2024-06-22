package com.qtech.check.mapper;

import com.qtech.check.pojo.AaListParamsCheckResult;
import org.apache.ibatis.annotations.Mapper;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/22 17:25:56
 * desc   :
 */


@Mapper
public interface AaListParamsReverseCtrlInfoMapper {

    public void insert(AaListParamsCheckResult aaListParamsCheckResult);
}
