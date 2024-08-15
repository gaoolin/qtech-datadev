package com.qtech.service.utils.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.Accessors;

/**
 * 响应信息主体
 *
 * @param
 * @description 此响应信息主体，专门用于返回数据给MES
 * @return
 */
public class R<T> extends ApiResponse<T> {

    @JsonProperty("Code")
    private int code;

    @JsonProperty("Msg")
    private String msg;

    private T data;

    @Override
    public int getCode() {
        return code;
    }

    // 实现链式调用
    @Override
    public R<T> setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public R<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public R<T> setData(T data) {
        this.data = data;
        return this;
    }
}