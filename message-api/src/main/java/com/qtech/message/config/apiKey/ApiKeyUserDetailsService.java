package com.qtech.message.config.apiKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 10:32:05
 * desc   :
 */

@Service
public class ApiKeyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String apiKey) {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
