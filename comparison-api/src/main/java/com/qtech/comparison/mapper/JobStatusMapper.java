package com.qtech.comparison.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qtech.comparison.entity.JobStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/11 10:38:26
 * desc   :
 */

@Repository
@Mapper
public interface JobStatusMapper extends BaseMapper<JobStatus> {

    JobStatus getDbJobRunDt(@Param("programName") String programName);

    JobStatus getDbJobRunStat(@Param("programName") String programName);
}
