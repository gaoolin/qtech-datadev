package com.qtech.service.exception;

import com.qtech.service.utils.response.ApiResponse;
import com.qtech.service.utils.response.R;
import com.qtech.service.utils.response.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;


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

    @ExceptionHandler(SimIdIgnoredException.class)
    @ResponseBody
    public R<String> handleSimIdIgnoredException(SimIdIgnoredException ex) {
        logger.error("SimIdIgnoredException: ", ex);
        return new R<String>()
                .setCode(ResponseCode.SUCCESS.getCode())
                .setMsg("Equipment reverse ignored")
                .setData(null);
    }

    @ExceptionHandler(ImChkException.class)
    @ResponseBody
    public R<String> handleImChkException(ImChkException ex) {
        logger.error("ImChkException: ", ex);
        return new R<String>()
                .setCode(ResponseCode.SUCCESS.getCode())
                .setMsg(ex.getMessage())
                .setData(null);
    }

    /**
     * @param ex
     * @param request
     * @return com.qtech.service.utils.response.ApiResponse<java.lang.String>
     * @description 参数校验异常处理，没有进入到控制器方法中，所以捕获不到这个异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<String> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        logger.error("ConstraintViolationException: ", ex);

        // 返回自定义的错误信息
        return new R<String>()
                .setCode(ResponseCode.SUCCESS.getCode())  // 你可以根据需要调整响应码
                .setMsg("simId validation failed.")
                .setData(null); // 将错误信息转换为字符串或根据需要处理
    }

    /**
     * @description 参数校验异常处理，没有进入到控制器方法中，所以捕获不到这个异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        logger.error("MethodArgumentNotValidException: ", ex);
        BindingResult result = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        result.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new R<String>()
                .setCode(ResponseCode.SUCCESS.getCode())
                .setMsg("simId validation failed")
                .setData(errors.toString());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Exception: ", ex);
        return new ApiResponse<>(ResponseCode.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
