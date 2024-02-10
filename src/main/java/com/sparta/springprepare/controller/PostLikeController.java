package com.sparta.springprepare.controller;

import com.sparta.springprepare.dto.PostLikeDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.PostLikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostLikeController {

    private PostLikeService postLikeService;

    // 로그인 사용자가 특정 게시물에 좋아요를 남기거나 취소할 수 있어야 한다.
    @PostMapping("/api/post/{postId}/like")
    public PostLikeDto onPostLike(@PathVariable(name="postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postLikeService.onPostLike(postId, userDetails.getUser());
    }

    // 본인이 작성한 게시물과 댓글에는 좋아요를 남길 수 없다.

}
