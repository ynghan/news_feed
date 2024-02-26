package com.sparta.springprepare.config.dummy;


import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {

    protected User newUser(String username, String password, String email) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode(password);
        return User.builder()
                .username(username)
                .password(encPassword)
                .email(email)
                .role(UserRoleEnum.USER)
                .createAt(LocalDateTime.now())
                .build();
    }

    protected User newMockUser(Long id, String username, String password, String email) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode(password);
        return User.builder()
                .id(id)
                .username(username)
                .password(encPassword)
                .email(email)
                .role(UserRoleEnum.USER)
                .createAt(LocalDateTime.now())
                .build();
    }
}