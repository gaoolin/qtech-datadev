package com.qtech.message.config.apiKey;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 10:31:50
 * desc   :
 */


public interface UserDetailsService {

    public UserDetails loadUserByUsername(String apiKey);

    public String getPassword();
}
