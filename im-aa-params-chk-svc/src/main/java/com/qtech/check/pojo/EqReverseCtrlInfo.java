package com.qtech.check.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/21 08:30:15
 * desc   :
 */

public class EqReverseCtrlInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String simId;
    private String source;
    private String prodType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date chkDt;

    private Integer code;
    private String description;

    @JsonIgnore // 指示Jackson序列化器忽略此字段
    transient private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public Date getChkDt() {
        return chkDt;
    }

    public void setChkDt(Date chkDt) {
        this.chkDt = chkDt;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @JsonIgnore
    public String getFormattedChkDt() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        return sdf.format(chkDt);
    }

    @Override
    public String toString() {
        return "EqReverseCtrlInfo{" +
                "id=" + id +
                ", simId='" + simId + '\'' +
                ", source='" + source + '\'' +
                ", prodType='" + prodType + '\'' +
                ", chkDt=" + chkDt +
                ", code=" + code +
                ", description='" + description + '\'' +
                ", version=" + version +
                '}';
    }
}
