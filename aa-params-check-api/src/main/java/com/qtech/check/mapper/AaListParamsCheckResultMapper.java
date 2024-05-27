package com.qtech.check.mapper;

import com.qtech.check.pojo.AaListParamsCheckResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/21 11:22:39
 * desc   :
 */

@Mapper
public interface AaListParamsCheckResultMapper {

    public int insertAaListParamsCheckResult(AaListParamsCheckResult record);

    public List<AaListParamsCheckResult> selectAaListParamsCheckResultList(AaListParamsCheckResult record);
}
