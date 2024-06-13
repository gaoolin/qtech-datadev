package com.qtech.olp.mapper;

import com.qtech.olp.entity.WbOlpCheckResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:56:46
 * desc   :
 */

@Mapper
public interface WbOlpCheckMapper {

    WbOlpCheckResult getOlpCheckResult(@Param("simId") String simId);
}
