package com.qtech.service.mapper.chk;

import com.qtech.service.entity.WbOlpChkResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:56:46
 * desc   :
 */

@Mapper
public interface WbOlpChkResultMapper {

    WbOlpChkResult getOlpChkResult(@Param("simId") String simId);
}
