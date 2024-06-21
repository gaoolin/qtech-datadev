package com.qtech.reverse.mapper;

import com.qtech.reverse.entity.WbOlpChkResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:56:46
 * desc   :
 */

@Mapper
public interface WbOlpChkMapper {

    WbOlpChkResult getOlpChkResult(@Param("simId") String simId);
}
