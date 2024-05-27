package com.qtech.check.mapper;

import com.qtech.check.pojo.AaListParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 16:43:43
 * desc   :
 */

@Mapper
public interface AaListParamsMapper {

    public List<AaListParams> selectAaListParamsList(AaListParams aaListParams);

    public int insertAaListParams(AaListParams aaListParams);
}
