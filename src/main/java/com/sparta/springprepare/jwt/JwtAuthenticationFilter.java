package com.sparta.springprepare.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springprepare.dto.userDto.UserReqDto;
import com.sparta.springprepare.dto.userDto.UserRespDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.util.CustomResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    // Post : /api/user/login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("디버그 : attemptAuthentication 호출됨");
        try {

            UserReqDto.LoginReqDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserReqDto.LoginReqDto.class);

            // 인증을 위한 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(
                    requestDto.getUsername(), requestDto.getPassword());

            // UserDetailsService의 loadUserByUsername 호출 -> db 확인
            // JWT를 쓴다 하더라도, 컨트롤러 진입을 하면 시큐리티의 권한체크, 인증체크의 도움을 받을 수 있게 세션을 만든다.
            // 이 세션의 유효기간은 request하고, response하면 끝!!
            Authentication authentication = getAuthenticationManager().authenticate(authenticationToken);
            System.out.println(authentication.toString());
            log.debug("-----------authentication 생성 ------------");
            return authentication;

        } catch (Exception e) {
            // unsuccessfulAthentication 호출함
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    // return authentication 잘 작동하면 해당 메서드 호출됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserDetailsImpl loginUser = (UserDetailsImpl) authResult.getPrincipal();
        String jwtToken = jwtUtil.createToken(loginUser);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtToken);
        UserRespDto.LoginRespDto loginRespDto = new UserRespDto.LoginRespDto(loginUser.getUser());
        CustomResponseUtil.success(response, loginRespDto);
    }

    // 로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        CustomResponseUtil.fail(response, "로그인 실패", HttpStatus.UNAUTHORIZED);
        response.sendRedirect("/user/login");
    }

}