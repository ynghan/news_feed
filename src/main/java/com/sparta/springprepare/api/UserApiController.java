package com.sparta.springprepare.api;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.userDto.*;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 사용자 회원 정보 불러오기
    @GetMapping("/info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return userService.getUserInfo(userDetails.getUser());
    }
    // 로그인 사용자 회원 정보 수정하기
    @PostMapping("/info")
    @ResponseBody
    public UserInfoDto postUserInfo(@RequestBody UserInfoDto userInfoDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return userService.postUserInfo(userInfoDto, user);
    }
    // 로그인 사용자 한줄 소개 불러오기
    @GetMapping("/info/introduce")
    @ResponseBody
    public UserIntroduceDto getUserIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getUserIntroduce(userDetails.getUser());
    }
    // 로그인 사용자 한줄 소개 수정하기
    @PostMapping("/info/introduce")
    @ResponseBody
    public UserInfoDto postUserIntroduce(@RequestBody UserInfoDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return userService.postUserIntroduce(dto, user);
    }
    // 로그인 사용자의 팔로우한 사람 수 불러오기
    @GetMapping("/followee/count")
    @ResponseBody
    public CountDto getUserFolloweeCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getFolloweeCount(userDetails.getUser());
    }
    // 로그인 사용자의 팔로우한 사람 수 불러오기
    @GetMapping("/follower/count")
    @ResponseBody
    public CountDto getUserFollowerCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getFollowerCount(userDetails.getUser());
    }
    // 로그인 사용자 프로필 사진 불러오기
    @GetMapping("/profile")
    @ResponseBody
    public ProfileEncodingDto getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        ProfileEncodingDto profile = userService.getProfile(userDetails.getUser());
        log.info("Profile: {}", profile.getProfile());  // 로깅 추가
        return profile;
    }
    // 로그인 사용자 프로필 사진 등록하기
    @PostMapping("/profile")
    @ResponseBody
    public ProfileDto postProfile(@RequestPart("file") MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.postProfile(file, userDetails.getUser());
    }

    //이미지 조회
    @GetMapping("/{username}/image")
    @ResponseBody
    public ProfileDto getUserPhotoUrl(@PathVariable(name="username") String username) {
        return userService.getPhotoUrl(username);
    }

    // 비밀번호 업데이트
    @PostMapping("/password")
    public UserRespDto.GeneralRespDto changePassword(@RequestBody @Valid UserReqDto.PasswordReqDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        return userService.changePassword(dto, loginUser);
    }
}
