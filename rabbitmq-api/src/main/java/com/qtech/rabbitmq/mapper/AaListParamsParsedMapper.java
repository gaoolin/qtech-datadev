package com.qtech.rabbitmq.mapper;

import com.qtech.rabbitmq.domain.AaListParams;
import org.apache.ibatis.annotations.Mapper;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/16 14:18:26
 * desc   :
 */

@Mapper
public interface AaListParamsParsedMapper {
    int save(AaListParams aaListParams);
}
