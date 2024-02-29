package com.sparta.springprepare.config.dummy;


import com.sparta.springprepare.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {

    protected User newEmptyUser(Long id, String username) {
        return User.builder()
                .id(id)
                .username(username)
                .createAt(LocalDateTime.now())
                .build();
    }


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


    protected Post newMockPost(Long id, String content, User user) {
        return Post.builder()
                .id(id)
                .content(content)
                .user(user)
                .build();
    }


    protected Comment newMockComment(Long id, String content, User user, Post post) {
        return Comment.builder()
                .id(id)
                .content(content)
                .user(user)
                .post(post)
                .build();
    }

    protected CommentLike newMockCommentLike(Long id, Comment comment, User user) {
        return CommentLike.builder()
                .id(id)
                .comment(comment)
                .user(user)
                .build();
    }

    protected PostLike newMockPostLike(Long id, Post post, User user) {
        return PostLike.builder()
                .id(id)
                .post(post)
                .user(user)
                .build();
    }

}