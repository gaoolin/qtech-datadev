package com.qtech.check.mapper;

import com.qtech.check.pojo.AaListParamsStdModelInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/07/22 14:24:31
 * desc   :
 */

@Mapper
public interface AaListParamsStdModelInfoMapper {
    public List<AaListParamsStdModelInfo> selectAaListParamsStdModelInfoList(AaListParamsStdModelInfo aaListParamsStdModelInfo);
}
