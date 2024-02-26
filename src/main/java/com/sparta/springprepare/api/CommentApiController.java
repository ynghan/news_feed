package com.sparta.springprepare.api;


import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.dto.ResponseDto;
import com.sparta.springprepare.dto.commentDto.CommentReqDto;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
import com.sparta.springprepare.service.comment.CommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 특정 사용자의 게시글의 댓글 조회하기
    @GetMapping("/{postId}/comment")
    public ResponseEntity<?> readCommentsOfPost(@PathVariable("postId") Long postId, Pageable pageable) {
        List<CommentRespDto> commentRespDtoList = commentService.findCommentOfPost(postId, pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "특정 사용자의 게시글의 댓글 조회하기 성공", commentRespDtoList), HttpStatus.OK);

    }
    // 특정 사용자의 게시글에 로그인 사용자의 댓글 등록하기
    @PostMapping("/{postId}/comment")
    public ResponseEntity<?> createCommentOfPost(@PathVariable(name="postId") Long postId, @RequestBody CommentReqDto dto, @AuthenticationPrincipal LoginUser loginUser) {
        CommentRespDto commentRespDto = commentService.createCommentOfPost(postId, dto, loginUser);
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 댓글 등록", commentRespDto), HttpStatus.OK);
    }
    // 로그인 사용자의 댓글 수정하기
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<?> updateCommentOfPost(@PathVariable(name="commentId") Long commentId, @RequestBody CommentReqDto dto, @AuthenticationPrincipal LoginUser loginUser) {
        CommentRespDto commentRespDto = commentService.updateCommentOfPost(commentId, dto, loginUser);
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 댓글 수정", commentRespDto), HttpStatus.OK);
    }
    // 로그인 사용자의 댓글 삭제하기
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteCommentOfPost(@PathVariable(name="commentId") Long commentId, @AuthenticationPrincipal LoginUser loginUser) {
        CommentRespDto commentRespDto = commentService.deleteCommentOfPost(commentId, loginUser);
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 사용자 댓글 삭제", commentRespDto), HttpStatus.OK);
    }

}
