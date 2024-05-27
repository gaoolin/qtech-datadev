package com.qtech.check.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/21 11:18:56
 * desc   :
 */

@Data
public class AaListParamsCheckResult {

    private String projectId;
    private String simId;
    private String prodType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date checkDt;
    private Integer statusCode;
    private String description;
}
