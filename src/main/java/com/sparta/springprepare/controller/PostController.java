package com.sparta.springprepare.controller;


import com.sparta.springprepare.dto.CountDto;
import com.sparta.springprepare.dto.PostRequestDto;
import com.sparta.springprepare.dto.PostResponseDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시물 등록하기
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return postService.createPost(requestDto, userDetails.getUser());
    }

    // 관심 게시물 조회하기
//    @GetMapping("/posts")
//    public List<PostResponseDto> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        // 응답 보내기
//        return postService.getPosts(userDetails.getUser());
//    }

    // 사용자 게시물 개수 조회하기
    @GetMapping("/post/count")
    public CountDto getPostCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPostCount(userDetails.getUser());
    }

}
