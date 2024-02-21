package com.sparta.springprepare.jwt;

import com.sparta.springprepare.domain.UserRoleEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.UnsupportedEncodingException;


//@SpringBootTest
//class JwtUtilTest {
//
//    JwtUtil jwtUtil = new JwtUtil();
//
//    @GetMapping("/create-jwt")
//    public String createJwt(HttpServletResponse res) throws UnsupportedEncodingException {
//        // Jwt 생성
//        String token = jwtUtil.createToken("Robbie", UserRoleEnum.USER);
//
//        // Jwt 쿠키 저장
////        jwtUtil.addJwtToCookie(token,res);
//
//        return "createJwt : " + token;
//    }
//
//    @GetMapping("/get-jwt")
//    public String getJwt(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String tokenValue) {
//        // JWT 토큰 substring
//        String token = jwtUtil.substringToken(tokenValue);
//
//        // 토큰 검증
//        if(!jwtUtil.validateToken(token)) {
//            throw new IllegalArgumentException("Token Error");
//        }
//
//        // 토큰에서 사용자 정보 가져오기
//        Claims info = jwtUtil.getUserInfoFromToken(token);
//
//        // 사용자 username
//        String username = info.getSubject();
//        System.out.println("username = " + username);
//
//        // 사용자 권한
//        String authority = (String) info.get(JwtUtil.AUTHORIZATION_KEY);
//        System.out.println("authority = " + authority);
//
//        return "getJwt : " + username + ", " + authority;
//    }
//
//}