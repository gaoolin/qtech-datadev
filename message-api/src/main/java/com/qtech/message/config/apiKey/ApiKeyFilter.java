package com.qtech.message.config.apiKey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.qtech.common.utils.StringUtils.isMatch;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 10:29:12
 * desc   :
 * 如果API密钥头 (apiKeyHeader) 为空或不存在，会调用 customAccessDeniedHandler 处理缺少API密钥的情况。这是一个合理的做法，因为没有有效的API密钥，系统无法识别请求的来源，所以拒绝访问是合理的。
 * customAccessDeniedHandler.handle(request, response, new AccessDeniedException("Missing API Key")) 会触发你自定义的访问拒绝处理器，向客户端返回一个关于“Missing API Key”的错误信息。这样做可以向客户端明确表示问题所在，而不是简单地返回一个通用的403 Forbidden响应。
 * 不过，考虑到你之前提到的配置，你可能希望某些路径不检查API密钥。在这种情况下，你应该在 isPathExempted 方法中处理这些路径，使得这些请求不会进入上述代码块，从而避免被拒绝。如果已经确定这些豁免路径在 configure(HttpSecurity http) 中正确处理，那么这段代码就应该是正确的，用于处理非豁免路径上缺少API密钥的请求。
 */

@Slf4j
@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Autowired
    private ApiKeyUserDetailsService apiKeyUserDetailsService;
    @Autowired
    private ExemptPathsConfig exemptPathsConfig;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String apiKeyHeader = request.getHeader("X-API-Key");

        // 先检查豁免路径
        if (isPathExempted(request, exemptPathsConfig.getExemptPaths())) {
            filterChain.doFilter(request, response);
            return;
        }

        // ...其他验证逻辑...
        if (apiKeyHeader == null || apiKeyHeader.isEmpty()) {
            customAccessDeniedHandler.handle(request, response, new AccessDeniedException("Missing API Key"));
            return;
        }

        try {
            UserDetails userDetails = apiKeyUserDetailsService.loadUserByUsername(apiKeyHeader);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (UsernameNotFoundException e) {
            customAccessDeniedHandler.handle(request, response, new AccessDeniedException("Invalid API Key"));
            log.error("Invalid API Key", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"invalid_api_key\",\"message\":\"Invalid API Key\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPathExempted(HttpServletRequest request, List<String> exemptPaths) {
        String path = request.getRequestURI();
        for (String exemptPath : exemptPaths) {
            if (isMatch(exemptPath, path)) {
                return true;
            }
        }
        return false;
    }
}
