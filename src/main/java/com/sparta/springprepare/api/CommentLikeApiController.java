package com.sparta.springprepare.api;


import com.sparta.springprepare.dto.CommentLikeDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.commetlike.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentLikeApiController {

    private final CommentLikeService commentLikeService;

    // 로그인 사용자가 댓글에 좋아요를 남긴다. 본인이 작성한 댓글에는 좋아요를 남길 수 없다.
    @PostMapping("/like/comment/{commentId}")
    public CommentLikeDto pushLikeToComment(@PathVariable(name="commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentLikeService.pushLikeToComment(commentId, userDetails.getUser());
    }


    // 로그인 사용자가 남긴 좋아요를 취소할 수 있어야 한다.
    @DeleteMapping("/like/comment/{commentId}")
    public CommentLikeDto deleteLikeToComment(@PathVariable(name="commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.deleteLikeToComment(commentId, userDetails.getUser());
    }

}
