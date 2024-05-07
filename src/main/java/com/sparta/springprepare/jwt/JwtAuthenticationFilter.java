package com.sparta.springprepare.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.dto.userDto.UserReqDto;
import com.sparta.springprepare.dto.userDto.UserRespDto;
import com.sparta.springprepare.util.CustomResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
    }

    // post의 /api/login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.debug("디버그 : JwtAuthenticationFilter attemptAuthentication()");

        try {
            ObjectMapper om = new ObjectMapper();
            UserReqDto.LoginReqDto loginReqDto = om.readValue(request.getInputStream(), UserReqDto.LoginReqDto.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(),
                    loginReqDto.getPassword());

            // loginUserService 호출 코드
            return authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            /*
             * InternalAuthenticationServiceException 해당 Exception으로 보내야
             * authenticationEntryPoint()로 처리됨.
             */
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        log.debug("디버그 : JwtAuthenticationFilter successfulAuthentication()");
        // 1. 세션에 있는 loginUser 가져오기
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();

        // 2. 세션값으로 토큰 생성
        String jwtToken = JwtProcess.create(loginUser);

//        String encodedToken = URLEncoder.encode(jwtToken, StandardCharsets.UTF_8);
        // 3. 쿠키 생성 및 설정
//        Cookie cookie = new Cookie(JwtVO.HEADER, encodedToken); // COOKIE_NAME은 쿠키의 이름을 나타내는 상수입니다.
//        cookie.setHttpOnly(true); // JavaScript를 통한 접근 방지
////        cookie.setSecure(true); // HTTPS 통신에서만 쿠키 전송
//        cookie.setPath("/"); // 쿠키가 전송될 수 있는 서버 경로
//        // 필요하다면 쿠키의 유효시간도 설정할 수 있습니다. 예: cookie.setMaxAge(60*60*24); // 24시간
//
//        // 쿠키를 응답에 추가
//        response.addCookie(cookie);

        // 3. 토큰을 헤더에 담기
        response.addHeader(JwtVO.HEADER, jwtToken);

        // 4. 토큰 담아서 성공 응답하기
        UserRespDto.LoginRespDto loginRespDto = new UserRespDto.LoginRespDto(loginUser.getUser());
        CustomResponseUtil.success(response, loginRespDto);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        CustomResponseUtil.fail(response, "로그인실패", HttpStatus.UNAUTHORIZED);
    }
}