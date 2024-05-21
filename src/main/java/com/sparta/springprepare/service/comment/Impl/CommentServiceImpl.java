package com.sparta.springprepare.service.comment.Impl;


import com.sparta.springprepare.auth.LoginUser;
import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.dto.commentDto.CommentReqDto;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
import com.sparta.springprepare.handler.ex.CustomApiException;
import com.sparta.springprepare.handler.ex.ErrorCode;
import com.sparta.springprepare.repository.comment.CommentRepository;
import com.sparta.springprepare.repository.post.PostRepository;
import com.sparta.springprepare.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 특정 게시판의 댓글 목록 조회하기
    @Transactional
    public List<CommentRespDto> findCommentOfPost(Long postId, Pageable pageable) {
        Post postPS = checkPost(postId);
        Page<Comment> comments = commentRepository.findByPost(postPS, pageable);
        List<CommentRespDto> resDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            resDtoList.add(new CommentRespDto(comment));
        }
        return resDtoList;
    }

    // 특정 게시판의 댓글 생성
    public CommentRespDto createCommentOfPost(Long postId, CommentReqDto dto, LoginUser loginUser) {

        Post findPost = checkPost(postId);

        Comment comment = new Comment();
        comment.patch(dto, findPost, loginUser.getUser());

        Comment savedComment = commentRepository.save(comment);

        return new CommentRespDto(savedComment);
    }

    @Transactional
    public CommentRespDto updateCommentOfPost(Long commentId, CommentReqDto dto, LoginUser loginUser) {
        Comment findComment = checkCommentById(commentId);
        if(!Objects.equals(findComment.getUser().getUsername(), loginUser.getUser().getUsername())) {
            throw new CustomApiException(ErrorCode.NOT_YOUR_COMMENT);
        }
        findComment.setContent(dto.getContent());
        Comment savedComment = commentRepository.save(findComment);
        return new CommentRespDto(savedComment);
    }

    public CommentRespDto deleteCommentOfPost(Long commentId, LoginUser loginUser) {
        Comment findComment = checkCommentById(commentId);
        if(!Objects.equals(findComment.getUser().getId(), loginUser.getUser().getId())) {
            throw new CustomApiException(ErrorCode.NOT_YOUR_COMMENT);
        }

        commentRepository.delete(findComment);
        return new CommentRespDto(findComment);
    }

    public List<CommentRespDto> getAllComments() {
        List<Comment> all = commentRepository.findAll();
        List<CommentRespDto> dtoList = new ArrayList<>();
        for (Comment comment : all) {
            dtoList.add(new CommentRespDto(comment));
        }
        return dtoList;
    }

    public CommentRespDto deleteComment(Long commentId) {
        Comment findComment = checkCommentById(commentId);
        commentRepository.delete(findComment);
        return new CommentRespDto(findComment);
    }

    public CommentRespDto updateComment(CommentReqDto requestDto, Long commentId) {
        Comment findComment = checkCommentById(commentId);
        findComment.setContent(requestDto.getContent());
        return new CommentRespDto(findComment);
    }


    public Comment checkCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CustomApiException(ErrorCode.COMMENT_NOT_EXIST));
    }

    public Post checkPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomApiException(ErrorCode.POST_NOT_EXIST));
    }
}
