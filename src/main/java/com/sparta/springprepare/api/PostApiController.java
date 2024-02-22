package com.sparta.springprepare.api;


import com.sparta.springprepare.dto.postDto.PostReqDto;
import com.sparta.springprepare.dto.postDto.PostRespDto;
import com.sparta.springprepare.dto.userDto.CountDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class PostApiController {

    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    // 로그인 사용자 게시물 등록하기
    @PostMapping("/posts")
    public PostRespDto createPost(@RequestBody PostReqDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return postService.createPost(requestDto, userDetails.getUser().getId());
    }

    // 로그인 사용자 게시물 조회하기
    @GetMapping("/posts")
    public List<PostRespDto> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        // 응답 보내기
        return postService.getPosts(userDetails.getUser(), pageable);
    }

    // 특정 사용자 게시물 조회하기 -> 조회 권한 체크 하기
    @GetMapping("/{followUsername}/posts")
    public List<PostRespDto> getUserPosts(@PathVariable(name="followUsername") String followUsername, @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getUserPosts(followUsername, userDetails.getUser(), pageable);
    }

    // 로그인 사용자 게시물 수정하기
    @PutMapping("/posts/{postId}/update")
    public PostRespDto updatePost(@PathVariable(name="postId") Long postId, @RequestBody PostReqDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(postId, requestDto, userDetails.getUser());
    }

    // 로그인 사용자의 "게시물 개수" 조회하기
    @GetMapping("/post/count")
    public CountDto getPostCount(@AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getPostCount(userDetails.getUser(), pageable);
    }

}
