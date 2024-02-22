package com.sparta.springprepare.service;

import com.sparta.springprepare.dto.commentDto.CommentReqDto;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
import com.sparta.springprepare.security.UserDetailsImpl;

import java.util.List;

public interface CommentService {
    // 특정 게시판의 댓글 목록 조회하기
    List<CommentRespDto> findCommentOfPost(Long postId);

    // 특정 게시판의 댓글 생성
    CommentRespDto createCommentOfPost(Long postId, CommentReqDto dto, UserDetailsImpl userDetails);

    CommentRespDto updateCommentOfPost(Long commentId, CommentReqDto dto, UserDetailsImpl userDetails);

    CommentRespDto deleteCommentOfPost(Long commentId, UserDetailsImpl userDetails);

    List<CommentRespDto> getAllComments();

    CommentRespDto deleteComment(Long commentId);

    CommentRespDto updateComment(CommentReqDto requestDto, Long commentId);

}