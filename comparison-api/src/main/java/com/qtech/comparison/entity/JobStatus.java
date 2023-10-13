package com.qtech.comparison.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/11 16:28:09
 * desc   :
 */

@ApiModel("spark作业信息对象")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class JobStatus {

    @ApiModelProperty("项目名称")
    String jobName;

    @ApiModelProperty("作业运行时间")
    String preRunTime;

    @ApiModelProperty("状态")
    String status;
}
