package com.sparta.springprepare.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springprepare.config.dummy.DummyObject;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.userDto.UserReqDto;
import com.sparta.springprepare.dto.userDto.UserRespDto;
import com.sparta.springprepare.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest extends DummyObject {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Spy
    private ObjectMapper om;


    @Test
    void signup_test() throws Exception {

        //given
        UserReqDto.JoinReqDto joinReqDto = new UserReqDto.JoinReqDto();
        joinReqDto.setUsername("dudgks56");
        joinReqDto.setPassword("dudgks56!!");
        joinReqDto.setEmail("daum4572@naver.com");

        // stub 1
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        // stub 2
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        // stub 3
        User dudgks = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        when(userRepository.save(any())).thenReturn(dudgks);
        //when
        UserRespDto.JoinRespDto joinRespDto = userService.signup(joinReqDto);
        String responseBody = om.writeValueAsString(joinRespDto);
        System.out.println("테스트 : " + responseBody);

        //then
        Assertions.assertThat(joinRespDto.getUsername()).isEqualTo("dudgks56");
    }
}