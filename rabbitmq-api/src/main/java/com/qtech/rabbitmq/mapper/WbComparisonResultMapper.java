package com.qtech.rabbitmq.mapper;

import com.qtech.rabbitmq.domain.WbComparisonResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/11 08:30:37
 * desc   :
 */


@Repository
@Mapper
public interface WbComparisonResultMapper {

    int add(WbComparisonResult wbComparisonResult);
}
