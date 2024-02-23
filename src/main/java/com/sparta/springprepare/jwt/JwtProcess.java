package com.sparta.springprepare.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtProcess {

    private static JwtVO jwtVO;
    private static final Logger log = LoggerFactory.getLogger(JwtProcess.class);

    public static String create(LoginUser loginUser) {
        log.debug("디버그 : JwtProcess create()");
        String jwtToken = JWT.create()
                .withSubject(loginUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIME))
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getRole().name())
                .sign(Algorithm.HMAC512(jwtVO.SECRET));
        return JwtVO.TOKEN_PREFIX + jwtToken;
    }

    public static LoginUser verify(String token) {
        log.debug("디버그 : JwtProcess verify()");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtVO.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder().id(id).role(UserRoleEnum.valueOf(role)).build();
        return new LoginUser(user);
    }
}
