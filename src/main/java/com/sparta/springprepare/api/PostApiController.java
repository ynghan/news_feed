package com.sparta.springprepare.api;


import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.dto.ResponseDto;
import com.sparta.springprepare.dto.postDto.PostReqDto;
import com.sparta.springprepare.dto.postDto.PostRespDto;
import com.sparta.springprepare.dto.userDto.CountDto;
import com.sparta.springprepare.service.post.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.sparta.springprepare.dto.postDto.PostRespDto.*;

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
    public ResponseEntity<?> createPost(@RequestBody PostReqDto requestDto, @AuthenticationPrincipal LoginUser loginUser) {
        // 응답 보내기
        PostRespDto postRespDto = postService.createPost(requestDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 게시물 등록", postRespDto), HttpStatus.OK);
    }

    // 로그인 사용자 게시물 목록 조회하기
    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(@AuthenticationPrincipal LoginUser loginUser, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        // 응답 보내기
        Page<PostRespDto> dtoPage = postService.getPosts(loginUser.getUser(), pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 게시물 조회", dtoPage), HttpStatus.OK);
    }

    // 로그인 사용자 특정 게시물 자세히 보기
    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> getDetailPostOfLoginUser(@AuthenticationPrincipal LoginUser loginUser, @PathVariable(name="postId") Long postId) {
        log.info(loginUser.getUser().getUsername());
        log.info(String.valueOf(postId));
          PostDetailRespDto detailPost = postService.getDetailPost(loginUser.getUser(), postId);
          return  new ResponseEntity<>(new ResponseDto<>(1, "특정 게시물 자세히 보기", detailPost), HttpStatus.OK);
    }

    // 특정 사용자 게시물 목록 조회하기 -> 조회 권한 체크 하기
    @GetMapping("/{followUsername}/posts")
    public ResponseEntity<?> getUserPosts(@PathVariable(name="followUsername") String followUsername, @AuthenticationPrincipal LoginUser loginUser, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostRespDto> dtoPage = postService.getUserPosts(followUsername, loginUser.getUser(), pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "특정 사용자 게시물 조회", dtoPage), HttpStatus.OK);
    }

    // 특정 사용자 특정 게시물 자세히 보기
//    public ResponseEntity<?> getDetailPostOfOtherUser() {
//
//    }

    // 로그인 사용자 게시물 수정하기
    @PutMapping("/posts/{postId}/update")
    public ResponseEntity<?> updatePost(@PathVariable(name="postId") Long postId, @RequestBody PostReqDto requestDto, @AuthenticationPrincipal LoginUser loginUser) {
        PostRespDto postRespDto = postService.updatePost(postId, requestDto, loginUser.getUser());
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 게시물 수정", postRespDto), HttpStatus.OK);
    }

    // 로그인 사용자의 "게시물 개수" 조회하기
    @GetMapping("/posts/count")
    public ResponseEntity<?> getPostCount(@AuthenticationPrincipal LoginUser loginUser, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
        CountDto postCount = postService.getPostCount(loginUser.getUser(), pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자의 게시물 개수 조회", postCount), HttpStatus.OK);
    }

}
