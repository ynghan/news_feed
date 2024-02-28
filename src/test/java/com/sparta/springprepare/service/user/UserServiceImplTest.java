package com.sparta.springprepare.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springprepare.config.dummy.DummyObject;
import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
import com.sparta.springprepare.dto.userDto.*;
import com.sparta.springprepare.repository.PasswordHistoryRepository;
import com.sparta.springprepare.repository.user.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest extends DummyObject {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHistoryRepository passwordHistoryRepository;

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
        assertThat(joinRespDto.getUsername()).isEqualTo("dudgks56");
    }

    @Test
    void getProfile_test() throws Exception {

        //given
        User user = newMockFullUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com", "/Users/jeong-yeonghan/work/news_feed/spring-prepare/src/main/resources/static/image/스크린샷 2024-01-09 오후 1.25.55.png");
        byte[] imageBytes = Files.readAllBytes(Paths.get(user.getProfile()));
        // stub 1
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //when
        ProfileEncodingDto profile = userService.getProfile(user);

        //then
        String imageBase64 = Base64.encodeBase64String(imageBytes);
        assertThat(profile.getProfile()).isEqualTo(imageBase64);
    }

    @Test
    void postProfile_test() throws Exception {
    }

    @Test
    void getFolloweeCount_test() throws Exception {
        //given
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "dudgks56@naver.com");

        user.setFollowees(Arrays.asList(new Follow(), new Follow(), new Follow()));
//        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //when
        CountDto countDto = userService.getFolloweeCount(user);

        //then
        assertThat(countDto.getCount()).isEqualTo(3);
    }

    @Test
    void getFollowerCount_test() throws Exception {

        //given
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "dudgks56@naver.com");

        user.setFollowers(Arrays.asList(new Follow(), new Follow(), new Follow()));
//        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //when
        CountDto countDto = userService.getFollowerCount(user);

        //then
        assertThat(countDto.getCount()).isEqualTo(3);

    }

    @Test
    void getUserInfo_test() throws Exception {
        //given
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "dudgks56@naver.com");

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        UserInfoDto userInfoDto = userService.getUserInfo(user);
        //then
        assertThat(userInfoDto.getUsername()).isEqualTo("dudgks56");
        assertThat(userInfoDto.getIntroduce()).isEqualTo(null);
        assertThat(userInfoDto.getNickname()).isEqualTo(null);
    }

    @Test
    void postUserInfo_test() throws Exception {
        //given
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "dudgks56@naver.com");
        UserInfoDto userInfoDto = new UserInfoDto("dudgks56", "age 27", "jjangoo");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        //when
        UserInfoDto userInfoRespDto = userService.postUserInfo(userInfoDto, user);

        //then
        assertThat(userInfoRespDto.getNickname()).isEqualTo("jjangoo");
    }

    @Test
    void getUserIntroduce_test() throws Exception {
        //given
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        UserIntroduceDto userIntroduceDto = userService.getUserIntroduce(user);
        //then
        assertThat(userIntroduceDto.getIntroduce()).isEqualTo(user.getIntroduce());
    }

    @Test
    void postUserIntroduce_test() throws Exception {
        //given
        String introduce = "hahahah";
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        userService.postUserIntroduce(introduce, user);
        //then
        assertThat(user.getIntroduce()).isEqualTo(introduce);
    }

    @Test
    void getPhtoUrl_test() throws Exception {


    }

    @Test
    void postPhoto_test() throws Exception {
        //given
        User user1 = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        String folderName = URLEncoder.encode("이미지 폴더", StandardCharsets.UTF_8);
        String fileName = "짱구.png";
        String fileUrl = "https://" + "ynghan-bucket" + ".s3.ap-northeast-2.amazonaws.com/" + folderName + "/" + fileName;

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user1));

        User user2 = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        user2.setPhotoImage(fileUrl);
        when(userRepository.save(any())).thenReturn(user2);

        //when
        PhotoDto photoDto = userService.postPhoto(fileUrl, user1);
        //then
        assertThat(photoDto.getUrl()).isEqualTo(user2.getPhotoImage());
    }

    @Test
    void findAllUsers_test() throws Exception {

        List<User> findUsers = Arrays.asList(
                User.builder().id(1L).username("aaaa").password("aaaa1!").introduce("aaaa").nickname("a1").build(),
                User.builder().id(2L).username("bbbb").password("bbbb1!").introduce("bbbb").nickname("b1").build(),
                User.builder().id(3L).username("cccc").password("cccc1!").introduce("cccc").nickname("c1").build());

        //stub
        when(userRepository.findAll()).thenReturn(findUsers);

        //when
        List<UserInfoDto> dtoList = userService.findAllUsers();

        //then
        assertThat(dtoList.get(0).getNickname()).isEqualTo("a1");
        assertThat(dtoList.get(1).getNickname()).isEqualTo("b1");
        assertThat(dtoList.get(2).getNickname()).isEqualTo("c1");
    }

    @Test
    void changePassword_test() throws Exception {
        //given
        UserReqDto.PasswordReqDto passwordReqDto = new UserReqDto.PasswordReqDto();
        passwordReqDto.setNewPassword("dudgks5669!!");
        passwordReqDto.setConfirmNewPassword("dudgks5669!!");

        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");

        //when
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        UserRespDto.GeneralRespDto generalRespDto = userService.changePassword(passwordReqDto, user);

        //then
        assertThat(generalRespDto.getId()).isEqualTo(1L);
        assertThat(generalRespDto.getUsername()).isEqualTo("dudgks56");
        assertThat(generalRespDto.getPassword()).isEqualTo(user.getPassword());

    }

    @Test
    void deleteUserByAdmin_test() throws Exception {
        //given
        String username = "dudgks56";

        User user1 = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        User user2 = newMockUser(2L, "dudgks", "dudgks56!!", "daum4527@naver.com");
        // stub
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));

        //when
        UserRespDto.GeneralRespDto generalRespDto = userService.deleteUserByAdmin(username);

        //then
        assertThat(generalRespDto.getId()).isEqualTo(user1.getId());
        assertThat(generalRespDto.getUsername()).isEqualTo(user1.getUsername());
        assertThat(generalRespDto.getPassword()).isEqualTo(user1.getPassword());

        // verify
        verify(userRepository).deleteById(user1.getId());
    }

    @Test
    void updateUserByAdmin_test() throws Exception {
        //given
        String username = "dudgks56";

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUsername("xodbs928");
        userInfoDto.setNickname("hahaha");
        userInfoDto.setIntroduce("kimtaeyoon 27 man");

        //stub
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User updateUser = newMockUser(1L, userInfoDto.getUsername(), userInfoDto.getNickname(), userInfoDto.getIntroduce());
        when(userRepository.save(user)).thenReturn(updateUser);

        //when
        UserRespDto.GeneralRespDto generalRespDto = userService.updateUserByAdmin(username, userInfoDto);

        //then
        assertThat(generalRespDto.getId()).isEqualTo(1L);
        assertThat(generalRespDto.getUsername()).isEqualTo("xodbs928");

    }

    @Test
    void changeUserToAdmin_test() throws Exception {
        //given
        String username = "dudgks56";

        // stub
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        //when
        AuthorityDto authorityDto = userService.changeUserToAdmin(username);
        //then
        assertThat(authorityDto.getRole()).isEqualTo(UserRoleEnum.ADMIN);
    }

    @Test
    void changeAdminToUser_test() throws Exception {
        //given
        String username = "dudgks56";

        // stub
        User user = newMockUser(1L, "dudgks56", "dudgks56!!", "daum4572@naver.com");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        //when
        AuthorityDto authorityDto = userService.changeAdminToUser(username);
        //then
        assertThat(authorityDto.getRole()).isEqualTo(UserRoleEnum.USER);
    }
}