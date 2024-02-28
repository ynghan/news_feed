package com.sparta.springprepare.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springprepare.dto.userDto.UserReqDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    public void 회원가입_test() throws Exception {
        //given
        UserReqDto.JoinReqDto requestDto = new UserReqDto.JoinReqDto();
        requestDto.setUsername("xodbs123");
        requestDto.setPassword("bi5686jr!");
        requestDto.setEmail("xodbs123@naver.com");
        String requestBody = om.writeValueAsString(requestDto);

        //when
        ResultActions resultActions = mvc.perform(post("/api/user/signup").content(requestBody).contentType(APPLICATION_JSON_UTF8));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(status().isCreated());
    }
}