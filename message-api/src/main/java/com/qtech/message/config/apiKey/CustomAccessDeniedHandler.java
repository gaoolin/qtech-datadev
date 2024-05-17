package com.qtech.message.config.apiKey;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 11:08:31
 * desc   :
 * 当已认证的用户尝试访问他们没有权限的资源时，这个类会被调用。您在这里设置了返回403 Forbidden状态码，
 * 并且返回了一个带有自定义错误消息的JSON响应，告诉客户端用户没有足够的权限访问所请求的资源。
 */


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 设置403 Forbidden状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置响应内容类型为JSON
        PrintWriter writer = response.getWriter();
        writer.write("{\"error\":\"access_denied\",\"message\":\"" + accessDeniedException.getMessage() + "\"}");
        writer.flush();
        writer.close();
    }
    // 实现你的逻辑
}
