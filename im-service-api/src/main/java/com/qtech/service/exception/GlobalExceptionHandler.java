package com.qtech.service.exception;

import com.qtech.service.utils.response.ApiResponse;
import com.qtech.service.utils.response.R;
import com.qtech.service.utils.response.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;


/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/06 14:10:12
 * desc   :
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<String> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        logger.error("IllegalArgumentException: ", ex);
        return new ApiResponse<>(ResponseCode.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<String> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        logger.error("ResourceNotFoundException: ", ex);
        return new ApiResponse<>(ResponseCode.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<String> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        // 这里处理 405 Method Not Allowed 异常
        logger.error("MethodNotAllowed: ", ex);
        return new ApiResponse<>(ResponseCode.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Exception: ", ex);
        return new ApiResponse<>(ResponseCode.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }

    @ExceptionHandler(SimIdIgnoredException.class)
    @ResponseBody
    public R<String> handleSimIdIgnoredException(SimIdIgnoredException ex) {
        return new R<String>()
            .setCode(ResponseCode.SUCCESS.getCode())
            .setMsg("Equipment reverse ignored")
            .setData(null);
    }
}
