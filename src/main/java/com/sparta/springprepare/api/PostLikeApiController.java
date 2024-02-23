package com.sparta.springprepare.api;

import com.sparta.springprepare.dto.PostLikeDto;
import com.sparta.springprepare.dto.ResponseDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.postlike.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostLikeApiController { // 비지니스 로직 서비스 계층으로 이동하기

    private final PostLikeService postLikeService;

    // 로그인 사용자가 특정 게시물에 좋아요를 남긴다. 본인이 작성한 게시물에는 좋아요를 남길 수 없다.
    @PostMapping("/like/post/{postId}")
    public ResponseEntity<?> onPostLike(@PathVariable(name="postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostLikeDto postLikeDto = postLikeService.onPostLike(postId, userDetails.getUser());

        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 특정 게시물에 좋아요", postLikeDto), HttpStatus.OK);
    }

    // 로그인 사용자는 특정 게시물에 좋아요를 취소할 수 있어야 한다.
    // 비지니스단 서비스 계층으로 이동
    @DeleteMapping("/like/post/{postId}")
    public ResponseEntity<?> deleteLikeToPost(@PathVariable(name="postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails, @PageableDefault(value=10)
    @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {

        PostLikeDto postLikeDto = postLikeService.deleteLikeToPost(postId, userDetails.getUser(), pageable);


        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 특정 게시물에 좋아요 취소", postLikeDto), HttpStatus.OK);
    }

}
