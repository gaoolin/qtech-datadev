package com.qtech.message.config.apiKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 10:32:05
 * desc   :
 */

@Service
public class ApiKeyUserDetailsService implements UserDetailsService {

    @Resource
    private ApiKeys apiKeys;

    @Override
    public UserDetails loadUserByUsername(String apiKey) throws UsernameNotFoundException {
        String username = apiKeys.getKeys().get(apiKey);
        if (username == null) {
            throw new UsernameNotFoundException("Invalid API Key");
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                "", // 密码可以留空，因为API密钥是主要的身份验证手段
                getAuthorities(username) // 根据用户名获取角色
        );
    }

    private List<GrantedAuthority> getAuthorities(String username) {
        switch (username) {
            case "admin":
                return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            case "user":
                return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            default:
                return Collections.emptyList(); // 返回一个空列表，表示没有角色
        }
    }
}
