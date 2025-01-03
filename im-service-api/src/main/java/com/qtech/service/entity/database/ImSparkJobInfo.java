package com.qtech.service.entity.database;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2025/01/03 14:33:16
 * desc   :
 */

@Data
public class ImSparkJobInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String jobName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime jobDt;
    private Integer statusCode;
    private String remark;
}
