package com.qtech.mq.domain;

import java.io.Serializable;

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
    private String chkDt;
    private Integer code;
    private String description;
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

    public String getChkDt() {
        return chkDt;
    }

    public void setChkDt(String chkDt) {
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

    @Override
    public String toString() {
        return "EqReverseCtrlInfo{" +
                "id=" + id +
                ", simId='" + simId + '\'' +
                ", source='" + source + '\'' +
                ", prodType='" + prodType + '\'' +
                ", chkDt='" + chkDt + '\'' +
                ", code=" + code +
                ", description='" + description + '\'' +
                ", version=" + version +
                '}';
    }
}
