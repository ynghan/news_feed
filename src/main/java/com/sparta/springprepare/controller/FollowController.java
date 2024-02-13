package com.sparta.springprepare.controller;


import com.sparta.springprepare.dto.FollowDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.FollowService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    // 특정 사용자(username)의 "팔로우" 목록 조회
    @GetMapping("/api/{username}/follower")
    public List<FollowDto> findFollowListOfUser(@PathVariable("username") String username) {
        return followService.findFollowListOfUser(username);
    }


    // 특정 사용자(username)의 "팔로위" 목록 조회
    @GetMapping("/api/{username}/followee")
    public List<FollowDto> findFolloweeUser(@PathVariable("username") String username) {
        return followService.findFolloweeListOfUser(username);
    }

    // 로그인 사용자의 "팔로우" 목록 조회
    @GetMapping("/api/follower")
    public List<FollowDto> findFollowListOfMine(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return followService.findFollowListOfUser(userDetails.getUsername());
    }

    // 로그인 사용자의 "팔로위" 목록 조회
    @GetMapping("/api/followee")
    public List<FollowDto> findFolloweeListOfMine(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return followService.findFolloweeListOfUser(userDetails.getUser().getUsername());
    }

    // 로그인 사용자가 특정 사용자(username)를 팔로우 -> 게시물의 조회 권한을 얻을 수 있다.
    @PostMapping("/api/{username}/follow")
    public FollowDto addFollower(@PathVariable(name="username") String followUsername, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return followService.addFollower(followUsername, userDetails.getUser());
    }

    // 로그인 사용자가 특정 사용자(username)를 팔로우 취소
    @DeleteMapping("/api/{username}/follow")
    public FollowDto deleteFolloweeUser(@PathVariable(name="username") String deleteUsername, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return followService.deleteFollowee(deleteUsername, userDetails.getUser());
    }

}
