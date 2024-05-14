package com.qtech.message.config.apiKey;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 10:29:12
 * desc   :
 */


@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final AccessDeniedHandler accessDeniedHandler;

    public ApiKeyFilter(UserDetailsService userDetailsService, AccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String apiKeyHeader = request.getHeader("X-API-Key");
        if (apiKeyHeader == null || apiKeyHeader.isEmpty()) {
            accessDeniedHandler.handle(request, response, new AccessDeniedException("Missing API Key"));
            return;
        }

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(apiKeyHeader);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (UsernameNotFoundException e) {
            accessDeniedHandler.handle(request, response, new AccessDeniedException("Invalid API Key"));
            return;
        }

        filterChain.doFilter(request, response);
    }
}
