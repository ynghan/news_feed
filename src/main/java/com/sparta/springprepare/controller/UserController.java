package com.sparta.springprepare.controller;


import com.sparta.springprepare.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String loginPage() {
        return "login";
    }
    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/user/info")
    public String getUserInfo() {
        return "user-info";
    }
}

