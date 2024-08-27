package com.qtech.mq.domain;

import java.util.Date;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/23 13:41:06
 * desc   :
 */


public class WbOlpRawData {
    private Date dt;
    private String simId;
    private String mcId;
    private Integer lineNo;
    private String leadX;
    private String leadY;
    private String padX;
    private String padY;
    private Integer checkPort;
    private Integer piecesIndex;
    private Date loadTime;

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public String getMcId() {
        return mcId;
    }

    public void setMcId(String mcId) {
        this.mcId = mcId;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public String getLeadX() {
        return leadX;
    }

    public void setLeadX(String leadX) {
        this.leadX = leadX;
    }

    public String getLeadY() {
        return leadY;
    }

    public void setLeadY(String leadY) {
        this.leadY = leadY;
    }

    public String getPadX() {
        return padX;
    }

    public void setPadX(String padX) {
        this.padX = padX;
    }

    public String getPadY() {
        return padY;
    }

    public void setPadY(String padY) {
        this.padY = padY;
    }

    public Integer getCheckPort() {
        return checkPort;
    }

    public void setCheckPort(Integer checkPort) {
        this.checkPort = checkPort;
    }

    public Integer getPiecesIndex() {
        return piecesIndex;
    }

    public void setPiecesIndex(Integer piecesIndex) {
        this.piecesIndex = piecesIndex;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }

    @Override
    public String toString() {
        return "WbOlpRawData{" +
                "dt=" + dt +
                ", simId='" + simId + '\'' +
                ", mcId='" + mcId + '\'' +
                ", lineNo=" + lineNo +
                ", leadX='" + leadX + '\'' +
                ", leadY='" + leadY + '\'' +
                ", padX='" + padX + '\'' +
                ", padY='" + padY + '\'' +
                ", checkPort=" + checkPort +
                ", piecesIndex=" + piecesIndex +
                ", loadTime=" + loadTime +
                '}';
    }
}
