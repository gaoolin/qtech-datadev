package com.qtech.comparison.mapper;

import com.qtech.comparison.entity.ComparisonResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:56:46
 * desc   :
 */

@Repository
@Mapper
public interface WbComparisonMapper {

    ComparisonResult getComparisonResult(@Param("simId") String simId);
}
