package com.sparta.springprepare.controller;


import com.sparta.springprepare.dto.userDto.CountDto;
import com.sparta.springprepare.dto.PostRequestDto;
import com.sparta.springprepare.dto.PostResponseDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 로그인 사용자 게시물 등록하기
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return postService.createPost(requestDto, userDetails.getUser().getId());
    }

    // 로그인 사용자 게시물 조회하기
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return postService.getPosts(userDetails.getUser());
    }

    // 특정 사용자 게시물 조회하기
    @GetMapping("/{userId}/posts")
    public List<PostResponseDto> getUserPosts(@PathVariable(name="userId") Long userId) {

        return postService.getUserPosts(userId);
    }

    // 로그인 사용자의 "게시물 개수" 조회하기
    @GetMapping("/post/count")
    public CountDto getPostCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPostCount(userDetails.getUser());
    }

}
