package com.qtech.message.config.apiKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 10:34:41
 * desc   :
 */


public class CustomUserDetails implements UserDetails {


    private String apiKey;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String apiKey, String authority) {
        this.apiKey = apiKey;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(authority));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return apiKey;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
