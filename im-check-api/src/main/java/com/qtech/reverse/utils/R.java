package com.qtech.reverse.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qtech.reverse.entity.EquipmentReverseControlInfo;

import java.io.Serializable;

/**
 * 响应信息主体
 */
public class R implements Serializable {
    /**
     * 成功
     */
    public static final int SUCCESS = HttpStatus.SUCCESS;
    /**
     * 失败
     */
    public static final int FAIL = HttpStatus.QTECH_IM_CHK_NG;
    private static final long serialVersionUID = 1L;

    @JsonProperty("Code")
    private int code;

    @JsonProperty("Msg")
    private String msg;

    @JsonProperty("data")
    private String data;

    public static R restResult(EquipmentReverseControlInfo data) {
        R apiResult = new R();
        if (data != null) {
            apiResult.setCode(data.getCode() == 0 ? SUCCESS : FAIL);
            apiResult.setMsg(data.getCode() == 0 ? "OK" : "Parameter Monitoring:" + data.getFormattedChkDt() + "|" + data.getDescription());
        } else {
            apiResult.setCode(SUCCESS);
            apiResult.setMsg("OK");
        }
        apiResult.setData(null);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
