package com.qtech.mq.serializer;

import java.util.Objects;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 10:42:37
 * desc   :
 */

public class Record {
    private String simId;
    private String prodType;
    private String chkDt;
    private String code;
    private String description;

    public Record(String simId, String prodType, String chkDt, String code, String description) {
        this.simId = simId;
        this.prodType = prodType;
        this.chkDt = chkDt;
        this.code = code;
        this.description = description;
    }

    public Record() {

    }

    // Getters and setters

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(simId, record.simId) && Objects.equals(prodType, record.prodType) && Objects.equals(chkDt, record.chkDt) && Objects.equals(code, record.code) && Objects.equals(description, record.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(simId, prodType, chkDt, code, description);
    }

    @Override
    public String toString() {
        return "Record{" +
                "simId='" + simId + '\'' +
                ", prodType='" + prodType + '\'' +
                ", chkDt='" + chkDt + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

