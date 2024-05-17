package com.qtech.message.config.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 09:32:07
 * desc   :
 */

//@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 这里应该连接到你的用户存储，例如数据库，获取用户信息
        // 示例代码：
        CustomUserDetails userDetails = new CustomUserDetails(username, "password", Arrays.asList("ROLE_USER"));
        return userDetails;
    }
}
