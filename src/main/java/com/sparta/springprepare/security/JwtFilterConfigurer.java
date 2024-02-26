package com.sparta.springprepare.security;

import com.sparta.springprepare.jwt.JwtAuthenticationFilter;
import com.sparta.springprepare.jwt.JwtAuthorizationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

// JWT 필터 등록이 필요함
public class JwtFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final AuthenticationManager authenticationManager;

    public JwtFilterConfigurer(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilter(new JwtAuthenticationFilter(authenticationManager))
                .addFilter(new JwtAuthorizationFilter(authenticationManager));
    }
}
