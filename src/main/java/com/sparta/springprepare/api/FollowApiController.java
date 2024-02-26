package com.sparta.springprepare.api;


import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.dto.FollowDto;
import com.sparta.springprepare.dto.ResponseDto;
import com.sparta.springprepare.service.follow.FollowService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FollowApiController {

    private final FollowService followService;

    public FollowApiController(FollowService followService) {
        this.followService = followService;
    }

    // 특정 사용자(username)의 "팔로우" 목록 조회
    @GetMapping("/{username}/follower")
    public ResponseEntity<?> findFollowListOfUser(@PathVariable("username") String username, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FollowDto> dtoPage = followService.findFollowListOfUser(username, pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "특정 사용자 팔로우 목록 확인", dtoPage), HttpStatus.OK);
    }


    // 특정 사용자(username)의 "팔로위" 목록 조회
    @GetMapping("/{username}/followee")
    public ResponseEntity<?> findFolloweeUser(@PathVariable("username") String username, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FollowDto> dtoPage = followService.findFolloweeListOfUser(username, pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "특정 사용자 팔로워 목록 조회", dtoPage), HttpStatus.OK);
    }

    // 로그인 사용자의 "팔로우" 목록 조회
    @GetMapping("/follower")
    public ResponseEntity<?> findFollowListOfMine(@AuthenticationPrincipal LoginUser loginUser, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FollowDto> dtoPage = followService.findFollowListOfUser(loginUser.getUsername(), pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 팔로우 목록 확인", dtoPage), HttpStatus.OK);
    }

    // 로그인 사용자의 "팔로위" 목록 조회
    @GetMapping("/followee")
    public ResponseEntity<?> findFolloweeListOfMine(@AuthenticationPrincipal LoginUser loginUser, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FollowDto> dtoPage = followService.findFolloweeListOfUser(loginUser.getUser().getUsername(), pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 팔로워 목록 확인", dtoPage), HttpStatus.OK);
    }

    // 로그인 사용자가 특정 사용자(username)를 팔로우 -> 게시물의 조회 권한을 얻을 수 있다.
    @PostMapping("/{username}/follow")
    public ResponseEntity<?> addFollower(@PathVariable(name="username") String followUsername, @AuthenticationPrincipal LoginUser loginUser) {
        FollowDto followDto = followService.addFollower(followUsername, loginUser.getUser());
        return new ResponseEntity<>(new ResponseDto<>(1, "", followDto), HttpStatus.OK);
    }

    // 로그인 사용자가 특정 사용자(username)를 팔로우 취소
    @DeleteMapping("/{username}/follow")
    public ResponseEntity<?> deleteFolloweeUser(@PathVariable(name="username") String deleteUsername, @AuthenticationPrincipal LoginUser loginUser) {
        FollowDto followDto = followService.deleteFollowee(deleteUsername, loginUser.getUser());
        return new ResponseEntity<>(new ResponseDto<>(1, "", followDto), HttpStatus.OK);
    }

}
