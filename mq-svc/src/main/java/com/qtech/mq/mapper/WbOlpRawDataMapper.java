package com.qtech.mq.mapper;

import com.qtech.mq.domain.WbOlpRawData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/23 13:39:50
 * desc   :
 */

@Mapper
public interface WbOlpRawDataMapper {
    public int addWbOlpRawDataBatch(@Param("wbOlpRawDataList") List<WbOlpRawData> wbOlpRawDataList);
}
