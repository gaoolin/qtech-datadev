package com.qtech.rabbitmq.mapper;

import com.qtech.rabbitmq.domain.WbOlpCheckResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/11 08:30:37
 * desc   :
 */


@Mapper
public interface WbOlpCheckResultMapper {

    int add(WbOlpCheckResult wbOlpCheckResult);
    void addBatch(List<WbOlpCheckResult> wbOlpCheckResults);
}
