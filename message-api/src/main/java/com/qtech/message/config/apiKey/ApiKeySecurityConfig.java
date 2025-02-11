package com.qtech.message.config.apiKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 10:36:34
 * desc   :
 */


@Configuration
@EnableWebSecurity
@Qualifier("apiKeySecurityConfig")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiKeySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApiKeyFilter apiKeyFilter;
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler; // 访问拒绝处理器 CustomAccessDeniedHandler
    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint; // 访问拒绝处理器 CustomAuthenticationEntryPoint

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // 具体的豁免路径
                .antMatchers("/message/api/list/**", "/message/api/glue/**", "/exempted/path2").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
