package com.sparta.springprepare.viewController;

import com.sparta.springprepare.auth.LoginUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal LoginUser loginUser) {
        // 페이지 동적 처리 : 사용자 이름
        String username = (loginUser != null && loginUser.getUser() != null) ? loginUser.getUser().getUsername() : "Guest";
        model.addAttribute("username", username);
        return "index";
    }
}
