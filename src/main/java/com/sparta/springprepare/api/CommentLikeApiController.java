package com.sparta.springprepare.api;


import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.dto.CommentLikeDto;
import com.sparta.springprepare.dto.ResponseDto;
import com.sparta.springprepare.service.commetlike.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentLikeApiController {

    private final CommentLikeService commentLikeService;

    // 로그인 사용자가 댓글에 좋아요를 남긴다. 본인이 작성한 댓글에는 좋아요를 남길 수 없다.
    @PostMapping("/like/comment/{commentId}")
    public ResponseEntity<?> pushLikeToComment(@PathVariable(name="commentId") Long commentId, @AuthenticationPrincipal LoginUser loginUser) {

        CommentLikeDto commentLikeDto = commentLikeService.pushLikeToComment(commentId, loginUser.getUser());
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 좋아요 등록", commentLikeDto), HttpStatus.OK);
    }


    // 로그인 사용자가 남긴 좋아요를 취소할 수 있어야 한다.
    @DeleteMapping("/like/comment/{commentId}")
    public ResponseEntity<?> deleteLikeToComment(@PathVariable(name="commentId") Long commentId, @AuthenticationPrincipal LoginUser loginUser) {
        CommentLikeDto commentLikeDto = commentLikeService.deleteLikeToComment(commentId, loginUser.getUser());
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 좋아요 취소", commentLikeDto), HttpStatus.OK);
    }

}
