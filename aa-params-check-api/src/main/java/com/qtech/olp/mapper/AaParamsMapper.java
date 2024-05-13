package com.qtech.olp.mapper;

import com.qtech.olp.entity.AaListMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 16:43:43
 * desc   :
 */

@Mapper
public interface AaParamsMapper {

    public List<AaListMessage> selectAaParamsList(AaListMessage aaListMessage);

    public int insertAaParams(AaListMessage aaListMessage);
}
