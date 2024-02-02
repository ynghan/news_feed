package com.sparta.springprepare.controller;


import com.sparta.springprepare.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

//    @GetMapping("/")
//    public String home(Model model) {
//        // 페이지 동적 처리 : 사용자 이름
//        model.addAttribute("username", "username");
//        return "index";
//    }

//    @GetMapping("/")
//    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        // 페이지 동적 처리 : 사용자 이름
//        model.addAttribute("username", userDetails.getUser().getUsername());
//        return "index";
//    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 페이지 동적 처리 : 사용자 이름
        String username = (userDetails != null && userDetails.getUser() != null) ? userDetails.getUser().getUsername() : "Guest";
        model.addAttribute("username", username);
        return "index";
    }

    @GetMapping("/posts")
    public String posts(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 페이지 동적 처리 : 사용자 이름, 게시물 개수, 팔로우 수, 팔로워 수, 게시물
        String username = (userDetails != null && userDetails.getUser() != null) ? userDetails.getUser().getUsername() : "Guest";
        model.addAttribute("username", username);
        return "post";
    }
}
