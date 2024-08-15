package com.qtech.check.mapper;

import com.qtech.check.pojo.AaListParamsStdModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/21 08:47:22
 * desc   :
 */

@Mapper
public interface AaListParamsStdModelMapper {

    public List<AaListParamsStdModel> selectAaListParamsStdModelList(AaListParamsStdModel aaListParamsStdModel);
}
