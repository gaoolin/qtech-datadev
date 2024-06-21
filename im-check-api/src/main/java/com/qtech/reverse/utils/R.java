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

    public static R ok() {
        return restResult(null, SUCCESS, "OK");
    }

    public static R ok(EquipmentReverseControlInfo data) {
        return restResult(data, SUCCESS, "OK");
    }

    public static R fail() {
        return restResult(null, FAIL, "操作失败");
    }

    public static R fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static R fail(EquipmentReverseControlInfo data) {
        return restResult(data, FAIL, "操作失败");
    }

    public static R fail(EquipmentReverseControlInfo data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static R fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static R restResult(EquipmentReverseControlInfo data, int code, String msg) {
        R apiResult = new R();
        apiResult.setCode(code);
        apiResult.setData(data.getCode() == 0 ? null : data.getFormattedChkDt() + ":" + data.getDescription());
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static Boolean isError(R ret) {
        return !isSuccess(ret);
    }

    public static Boolean isSuccess(R ret) {
        return R.SUCCESS == ret.getCode();
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
