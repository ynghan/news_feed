package com.sparta.springprepare.service;

import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.dto.commentDto.CommentRequestDto;
import com.sparta.springprepare.dto.commentDto.CommentResponseDto;
import com.sparta.springprepare.repository.CommentRepository;
import com.sparta.springprepare.repository.PostRepository;
import com.sparta.springprepare.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // 특정 게시판의 댓글 목록 조회하기
    @Transactional
    public List<CommentResponseDto> findCommentOfPost(Long postId) {
        List<Comment> commentList = postRepository.findById(postId).get().getComments();
        List<CommentResponseDto> resDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            resDtoList.add(new CommentResponseDto(comment));
        }
        return resDtoList;
    }

    // 특정 게시판의 댓글 생성
    public CommentResponseDto createCommentOfPost(Long postId, CommentRequestDto dto, UserDetailsImpl userDetails) {

        Post findPost = postRepository.findById(postId).get();

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setPost(findPost);
        comment.setUser(userDetails.getUser());

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    @Transactional
    public CommentResponseDto updateCommentOfPost(Long commentId, CommentRequestDto dto, UserDetailsImpl userDetails) {
        Comment findComment = commentRepository.findById(commentId).get();
        if(!Objects.equals(findComment.getUser().getUsername(), userDetails.getUser().getUsername())) {
            throw new IllegalArgumentException("등록한 댓글이 아닙니다.");
        }
        findComment.setContent(dto.getContent());
        Comment savedComment = commentRepository.save(findComment);
        return new CommentResponseDto(savedComment);
    }

    public CommentResponseDto deleteCommentOfPost(Long commentId, UserDetailsImpl userDetails) {
        Comment findComment = commentRepository.findById(commentId).get();
        commentRepository.delete(findComment);
        return new CommentResponseDto(findComment);
    }

    public List<CommentResponseDto> findAll() {
        List<Comment> all = commentRepository.findAll();
        List<CommentResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : all) {
            dtoList.add(new CommentResponseDto(comment));
        }
        return dtoList;
    }
}
