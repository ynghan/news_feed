package com.sparta.springprepare.api;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.ResponseDto;
import com.sparta.springprepare.dto.userDto.*;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }


    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserReqDto.JoinReqDto requestDto, BindingResult bindingResult) { // ajax로 로그인하기 때문에 @RequestBody
        log.info(requestDto.getUsername());
        // Validation 예외처리
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!fieldErrors.isEmpty()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        User userPS = userService.signup(requestDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", new UserRespDto.JoinRespDto(userPS)), HttpStatus.CREATED);
    }

    // 로그인 사용자 회원 정보 불러오기
    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        UserInfoDto userInfoDto = userService.getUserInfo(userDetails.getUser());
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 회원 정보 불러오기 성공", userInfoDto), HttpStatus.OK);
    }
    // 로그인 사용자 회원 정보 수정하기
    @PostMapping("/info")
    @ResponseBody
    public ResponseEntity<?> postUserInfo(@RequestBody UserInfoDto userInfoDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        UserInfoDto userInfoDtoPS = userService.postUserInfo(userInfoDto, user);
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 회원 정보 수정하기 성공", userInfoDtoPS), HttpStatus.OK);
    }
    // 로그인 사용자 한줄 소개 불러오기
    @GetMapping("/info/introduce")
    @ResponseBody
    public ResponseEntity<?> getUserIntroduce(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserIntroduceDto userIntroduceDto = userService.getUserIntroduce(userDetails.getUser());
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 한줄 소개 불러오기 성공", userIntroduceDto), HttpStatus.OK);
    }
    // 로그인 사용자 한줄 소개 수정하기
    @PostMapping("/info/introduce")
    @ResponseBody
    public ResponseEntity<?> postUserIntroduce(@RequestBody UserInfoDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        UserInfoDto userInfoDto = userService.postUserIntroduce(dto, user);
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 한줄 소개 수정하기 성공", userInfoDto), HttpStatus.OK);
    }
    // 로그인 사용자의 팔로우한 사람 수 불러오기
    @GetMapping("/followee/count")
    @ResponseBody
    public ResponseEntity<?> getUserFolloweeCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CountDto countDto = userService.getFolloweeCount(userDetails.getUser());
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자의 팔로우한 사람 수 불러오기 성공", countDto), HttpStatus.OK);
    }
    // 로그인 사용자의 팔로우한 사람 수 불러오기
    @GetMapping("/follower/count")
    @ResponseBody
    public ResponseEntity<?> getUserFollowerCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CountDto countDto = userService.getFollowerCount(userDetails.getUser());
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자의 팔로우한 사람 수 불러오기 성공", countDto), HttpStatus.OK);
    }
    // 로그인 사용자 프로필 사진 불러오기
    @GetMapping("/profile")
    @ResponseBody
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        ProfileEncodingDto profile = userService.getProfile(userDetails.getUser());
        log.info("Profile: {}", profile.getProfile());  // 로깅 추가
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 프로필 사진 불러오기 성공", profile), HttpStatus.OK);
    }
    // 로그인 사용자 프로필 사진 등록하기
    @PostMapping("/profile")
    @ResponseBody
    public ResponseEntity<?> postProfile(@RequestPart("file") MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileDto profileDto = userService.postProfile(file, userDetails.getUser());
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 프로필 사진 등록하기 성공", profileDto), HttpStatus.OK);
    }

    //이미지 조회
    @GetMapping("/{username}/image")
    @ResponseBody
    public ResponseEntity<?> getUserPhotoUrl(@PathVariable(name="username") String username) {
        ProfileDto photoDto = userService.getPhotoUrl(username);
        return new ResponseEntity<>(new ResponseDto<>(1, "이미지 조회 성공", photoDto), HttpStatus.OK);
    }

    // 비밀번호 업데이트
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid UserReqDto.PasswordReqDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        UserRespDto.GeneralRespDto generalRespDto = userService.changePassword(dto, loginUser);
        return new ResponseEntity<>(new ResponseDto<>(1, "비밀번호 업데이트 성공", generalRespDto), HttpStatus.OK);
    }
}
