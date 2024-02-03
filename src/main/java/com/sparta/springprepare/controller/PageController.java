package com.sparta.springprepare.controller;


import com.sparta.springprepare.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/page")
public class PageController {

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

    // 게시판 페이지
    @GetMapping("/feed")
    public String feedPage() {
        return "feed";
    }

    // 게시물 작성 페이지
    @GetMapping("/posting")
    public String createPost(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = (userDetails != null && userDetails.getUser() != null) ? userDetails.getUser().getUsername() : "Guest";
        model.addAttribute("username", username);
        return "posting";
    }

    @GetMapping("/user/info")
    public String getUserInfo() {
        return "user-info";
    }


}
