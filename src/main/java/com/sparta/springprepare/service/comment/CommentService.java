package com.sparta.springprepare.service.comment;

import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.dto.commentDto.CommentReqDto;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    // 특정 게시판의 댓글 목록 조회하기
    List<CommentRespDto> findCommentOfPost(Long postId, Pageable pageable);

    // 특정 게시판의 댓글 생성
    CommentRespDto createCommentOfPost(Long postId, CommentReqDto dto, LoginUser loginUser);

    CommentRespDto updateCommentOfPost(Long commentId, CommentReqDto dto, LoginUser loginUser);

    CommentRespDto deleteCommentOfPost(Long commentId, LoginUser loginUser);

    List<CommentRespDto> getAllComments();

    CommentRespDto deleteComment(Long commentId);

    CommentRespDto updateComment(CommentReqDto requestDto, Long commentId);

}