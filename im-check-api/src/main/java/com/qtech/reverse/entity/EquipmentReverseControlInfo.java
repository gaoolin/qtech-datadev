package com.qtech.reverse.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/21 08:30:15
 * desc   :
 */

@Data
public class EquipmentReverseControlInfo {
    private String simId;
    private String source;
    private String prodType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date chkDt;
    private Integer code;
    private String description;
    private String label;

    public String getFormattedChkDt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        return sdf.format(chkDt);
    }
}
