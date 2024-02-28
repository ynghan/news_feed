package com.sparta.springprepare.config.dummy;


import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {

    protected User newLoginUser(Long id, UserRoleEnum role) {
        return User.builder()
                .id(id)
                .role(role)
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

    protected User newMockFullUser(Long id, String username, String password, String email, String profile) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode(password);
        return User.builder()
                .id(id)
                .username(username)
                .password(encPassword)
                .email(email)
                .profile(profile)
                .role(UserRoleEnum.USER)
                .createAt(LocalDateTime.now())
                .build();
    }

    protected Follow newMockFollow(Long id, User follower, User followee) {
        return Follow.builder()
                .id(id)
                .follower(follower)
                .followee(followee)
                .build();
    }


}