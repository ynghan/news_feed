package com.sparta.springprepare.controller;


import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.userDto.*;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        log.info(requestDto.getUsername());
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!fieldErrors.isEmpty()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/page/user/signup";
        }

        userService.signup(requestDto);

        return "redirect:/api/page/user/login";
    }
    // 회원 정보 불러오기
    @GetMapping("/info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return userService.getUserInfo(userDetails.getUser());
    }
    // 회원 정보 수정하기
    @PostMapping("/info")
    @ResponseBody
    public UserInfoDto postUserInfo(@RequestBody UserInfoDto userInfoDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return userService.postUserInfo(userInfoDto, user);
    }
    // 로그인 회원 한줄 소개 불러오기
    @GetMapping("/info/introduce")
    @ResponseBody
    public UserIntroduceDto getUserIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getUserIntroduce(userDetails.getUser());
    }
    // 로그인 회원 한줄 소개 수정하기
    @PostMapping("/info/introduce")
    @ResponseBody
    public UserInfoDto postUserIntroduce(@RequestBody UserInfoDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return userService.postUserIntroduce(dto, user);
    }
    // 로그인 회원을 팔로우한 사람 수 불러오기
    @GetMapping("/followee/count")
    @ResponseBody
    public CountDto getUserFolloweeCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getFolloweeCount(userDetails.getUser());
    }
    // 로그인 회원이 팔로우한 사람 수 불러오기
    @GetMapping("/follower/count")
    @ResponseBody
    public CountDto getUserFollowerCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getFollowerCount(userDetails.getUser());
    }
    // 로그인 회원 프로필 사진 불러오기
    @GetMapping("/profile")
    @ResponseBody
    public ProfileEncodingDto getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        ProfileEncodingDto profile = userService.getProfile(userDetails.getUser());
        log.info("Profile: {}", profile.getProfile());  // 로깅 추가
        return profile;
    }
    // 로그인 회원 프로필 사진 등록하기
    @PostMapping("/profile")
    @ResponseBody
    public ProfileDto postProfile(@RequestPart("file")MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.postProfile(file, userDetails.getUser());
    }

    //    @PostMapping("/password")
//    public PasswordDto changePassword(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//        userService.changePassword()
//    }
}

