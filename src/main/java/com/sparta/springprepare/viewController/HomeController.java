package com.sparta.springprepare.viewController;

import com.sparta.springprepare.auth.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal LoginUser loginUser, HttpServletRequest request) {
        // 페이지 동적 처리 : 사용자 이름
        String username = (loginUser != null && loginUser.getUser() != null) ? loginUser.getUser().getUsername() : "Guest";
        model.addAttribute("username", username);

        // 쿠키 정보 로그 출력
        String authCookie = request.getCookies() != null ? Arrays.stream(request.getCookies())
                .filter(c -> "Authorization".equals(c.getName()))
                .findFirst()
                .map(c -> c.getValue())
                .orElse("Not Found") : "Cookies Not Found";
        logger.info("Authorization Cookie: {}", authCookie);

        return "index"; // 홈 화면 렌더링 할 때, 피
    }
}
