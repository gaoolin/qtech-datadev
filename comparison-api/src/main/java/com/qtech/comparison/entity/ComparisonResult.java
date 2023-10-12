package com.qtech.comparison.entity;

import io.swagger.annotations.ApiModel;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 11:52:38
 * desc   :  实体类
 */

@ApiModel("打线图比对结果对象")
public class ComparisonResult {

    public String simId;

    public String programName;

    public String dt;

    public String code;

    public String description;

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

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        return "ComparisonResult{" +
                "simId='" + simId + '\'' +
                ", programName='" + programName + '\'' +
                ", dt='" + dt + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
