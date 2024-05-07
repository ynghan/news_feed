package com.sparta.springprepare.jwt;

import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProcessTest {

    @Test
    void create_test() throws Exception {
        //given
        User user = User.builder().id(1L).role(UserRoleEnum.ADMIN).build();
        LoginUser loginUser = new LoginUser(user);

        //when
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("테스트 : " + jwtToken);

        //then
        Assertions.assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));

    }

    @Test
    void verify_test() throws Exception {
        //given
        String jwtToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOm51bGwsImV4cCI6MTcxNTY4NzM3NiwiaWQiOjEsInVzZXJuYW1lIjpudWxsLCJyb2xlIjoiQURNSU4ifQ.LwyQ3fd-80zrrf125usAtKFgVJgCvJ78kZzcrNxDMUZnhJ3cGx3_F2eVAzgCA_J5bPzL26MTomduV74HdewGnA";

        //when
        LoginUser loginUser = JwtProcess.verify(jwtToken);
        System.out.println("테스트 : " + loginUser.getUser().getId());
        System.out.println("테스트 : " + loginUser.getUser().getRole().name());

        //then
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
        assertThat(loginUser.getUser().getRole()).isEqualTo(UserRoleEnum.ADMIN);

    }
}