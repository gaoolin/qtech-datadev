package com.qtech.olp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qtech.olp.entity.ComparisonResult;
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
public interface ComparisonMapper extends BaseMapper<ComparisonResult> {

    ComparisonResult getComparisonResult(@Param("simId") String simId);
}
