package com.sparta.springprepare.controller;


import com.sparta.springprepare.dto.commentDto.CommentRequestDto;
import com.sparta.springprepare.dto.commentDto.CommentResponseDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 특정 사용자의 게시글의 댓글 조회하기
    @GetMapping("/{postId}/comment")
    public List<CommentResponseDto> readCommentsOfPost(@PathVariable("postId") Long postId) {
        return commentService.findCommentOfPost(postId);
    }
    // 특정 사용자의 게시글에 로그인 사용자의 댓글 등록하기
    @PostMapping("/{postId}/comment")
    public CommentResponseDto createCommentOfPost(@PathVariable(name="postId") Long postId, @RequestBody CommentRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createCommentOfPost(postId, dto, userDetails);
    }
    // 로그인 사용자의 댓글 수정하기
    @PutMapping("/comment/{commentId}")
    public CommentResponseDto updateCommentOfPost(@PathVariable(name="commentId") Long commentId, @RequestBody CommentRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateCommentOfPost(commentId, dto, userDetails);
    }
    // 로그인 사용자의 댓글 삭제하기
    @DeleteMapping("/comment/{commentId}")
    public CommentResponseDto deleteCommentOfPost(@PathVariable(name="commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteCommentOfPost(commentId, userDetails);
    }


}
