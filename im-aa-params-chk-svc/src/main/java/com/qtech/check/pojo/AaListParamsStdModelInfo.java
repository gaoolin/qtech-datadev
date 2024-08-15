package com.qtech.check.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/07/22 14:20:43
 * desc   :
 */

@Data
@Accessors(chain = true)
public class AaListParamsStdModelInfo {
    private Long id;
    private String prodType;
    private Integer listParams;
    private Integer itemParams;
    private Integer status;
    private String provider;
    private String belongTo;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
}
