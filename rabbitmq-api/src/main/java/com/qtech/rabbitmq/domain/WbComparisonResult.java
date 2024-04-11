package com.qtech.rabbitmq.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/04/10 16:53:17
 * desc   :  打线图比对结果
 */

public class WbComparisonResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private String simId;
    private String programName;
    private String mcId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dt;
    private Integer code;
    private String description;

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getMcId() {
        return mcId;
    }

    public void setMcId(String mcId) {
        this.mcId = mcId;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WbComparisonResult{" +
                "simId='" + simId + '\'' +
                ", programName='" + programName + '\'' +
                ", mcId='" + mcId + '\'' +
                ", dt=" + dt +
                ", code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}
