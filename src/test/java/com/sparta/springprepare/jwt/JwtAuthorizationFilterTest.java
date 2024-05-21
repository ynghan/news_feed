package com.sparta.springprepare.jwt;

import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class JwtAuthorizationFilterTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void authorization_success_test() throws Exception {
        //given
        User user = User.builder().id(1L).role(UserRoleEnum.USER).build();
        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("테스트 : " + jwtToken);

        //when
        ResultActions resultActions = mvc.perform(get("/api/user/hello/test").header(JwtVO.HEADER, jwtToken)); //인증이 필요한 주소 사용

        //then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void authorization_fail_test() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(get("/api/user/hello/test")); //인증이 필요한 주소 사용

        //then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    void authorization_admin_test() throws Exception {
        //given
        User user = User.builder().id(1L).role(UserRoleEnum.USER).build();
        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("테스트 : " + jwtToken);

        //when
        ResultActions resultActions = mvc.perform(get("/api/admin/hello/test").header(JwtVO.HEADER, jwtToken)); //인증이 필요한 주소 사용

        //then
        resultActions.andExpect(status().isForbidden());
    }
}