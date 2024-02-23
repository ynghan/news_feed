package com.sparta.springprepare.api;


import com.sparta.springprepare.dto.commentDto.CommentReqDto;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.comment.CommentService;
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
    public List<CommentRespDto> readCommentsOfPost(@PathVariable("postId") Long postId) {
        return commentService.findCommentOfPost(postId);
    }
    // 특정 사용자의 게시글에 로그인 사용자의 댓글 등록하기
    @PostMapping("/{postId}/comment")
    public CommentRespDto createCommentOfPost(@PathVariable(name="postId") Long postId, @RequestBody CommentReqDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createCommentOfPost(postId, dto, userDetails);
    }
    // 로그인 사용자의 댓글 수정하기
    @PutMapping("/comment/{commentId}")
    public CommentRespDto updateCommentOfPost(@PathVariable(name="commentId") Long commentId, @RequestBody CommentReqDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateCommentOfPost(commentId, dto, userDetails);
    }
    // 로그인 사용자의 댓글 삭제하기
    @DeleteMapping("/comment/{commentId}")
    public CommentRespDto deleteCommentOfPost(@PathVariable(name="commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteCommentOfPost(commentId, userDetails);
    }


}
