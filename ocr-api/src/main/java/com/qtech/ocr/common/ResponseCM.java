package com.qtech.ocr.common;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 13:59:46
 * desc   :  HTTP 状态码 和一些标准的 RESTful API 编码实践：
 *
 * 2xx 系列表示成功（如 200 OK, 201 Created）
 * 4xx 系列表示客户端错误（如 400 Bad Request, 401 Unauthorized, 404 Not Found）
 * 5xx 系列表示服务器错误（如 500 Internal Server Error）
 */

public enum ResponseCode {
    SUCCESS(200, "Success"),
    CREATED(201, "Created"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable");

    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}