package com.qtech.ocr.common;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 13:53:21
 * desc   :  响应数据
 */

public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    public ApiResponse(ResponseCM responseCM, T data) {
        this.code = responseCM.getCode();
        this.msg = responseCM.getMsg();
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}