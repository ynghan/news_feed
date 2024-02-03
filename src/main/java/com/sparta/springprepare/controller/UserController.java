package com.sparta.springprepare.controller;


import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.*;
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
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
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

    // 회원 관련 정보 받기
    @GetMapping("/info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getUserInfo(userDetails.getUser());
    }

    @PostMapping("/info")
    @ResponseBody
    public void postUserInfo(UserInfoDto userInfoDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userService.postUserInfo(userInfoDto, user);
    }
    @PostMapping("/info/introduce")
    @ResponseBody
    public void postUserIntroduce(@RequestParam("introduce") String introduce, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userService.postUserIntroduce(introduce, user);
    }
    @GetMapping("/info/introduce")
    @ResponseBody
    public UserIntroduceDto getUserIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getUserIntroduce(userDetails.getUser());
    }
    @GetMapping("/followee/count")
    @ResponseBody
    public CountDto getUserFolloweeCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getFolloweeCount(userDetails.getUser());
    }

    @GetMapping("/follower/count")
    @ResponseBody
    public CountDto getUserFollowerCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getFollowerCount(userDetails.getUser());
    }

    @GetMapping("/profile")
    @ResponseBody
    public ProfileEncodingDto getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        ProfileEncodingDto profile = userService.getProfile(userDetails.getUser());
        log.info("Profile: {}", profile.getProfile());  // 로깅 추가
        return profile;
    }

    @PostMapping("/profile")
    @ResponseBody
    public ProfileDto postProfile(@RequestPart("file")MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return userService.postProfile(file, userDetails.getUser());

    }
}

