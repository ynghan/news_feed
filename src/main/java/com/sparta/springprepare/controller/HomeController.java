package com.sparta.springprepare.controller;

import com.sparta.springprepare.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 페이지 동적 처리 : 사용자 이름
        String username = (userDetails != null && userDetails.getUser() != null) ? userDetails.getUser().getUsername() : "Guest";
        model.addAttribute("username", username);
        return "index";
    }
}
