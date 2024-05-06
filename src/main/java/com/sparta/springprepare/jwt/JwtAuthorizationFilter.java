package com.sparta.springprepare.jwt;

import com.sparta.springprepare.auth.LoginUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static com.sparta.springprepare.jwt.JwtVO.TOKEN_PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.debug("디버그 : JwtAuthorizationFilter doFilterInternal()");
        String token = extractToken(request);
        if (!token.isEmpty()) {
            log.info("token 값이 담겼는지 확인 : " + token);
            try {
                LoginUser loginUser = JwtProcess.verify(token); // 토큰 검증 시도
                // 임시 세션 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.debug("토큰 검증 실패: " + e.getMessage());
            }
        } else {
            log.debug("토큰이 비어있습니다.");
        }
        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String token = "";
        String header = request.getHeader(JwtVO.HEADER);
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            token = header;
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("Authorization".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        if (StringUtils.hasText(token) && token.startsWith("Bearer%20")) {
            // URL 인코딩된 공백(%20)을 실제 공백으로 치환하고, "Bearer " 부분을 제거
            return token.replaceFirst("Bearer%20", "").trim();
        }
        return token;
    }
}
