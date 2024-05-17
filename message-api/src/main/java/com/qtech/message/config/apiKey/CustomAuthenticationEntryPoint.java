package com.qtech.message.config.apiKey;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 10:37:06
 * desc   :
 * 此实现是AuthenticationEntryPoint的实例，它在尚未认证的用户尝试访问需要认证的资源时被调用。
 * 这里简单地返回了403 Forbidden状态码，并附带了一个默认的错误信息“Access Denied”。
 */


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}
