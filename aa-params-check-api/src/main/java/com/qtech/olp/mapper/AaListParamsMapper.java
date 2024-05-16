package com.qtech.olp.mapper;

import com.qtech.olp.entity.AaListParamsMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 16:43:43
 * desc   :
 */

@Repository
public interface AaListParamsMapper {

    public List<AaListParamsMessage> selectAaListParamsList(AaListParamsMessage aaListParamsMessage);

    public int insertAaListParams(AaListParamsMessage aaListParamsMessage);
}
