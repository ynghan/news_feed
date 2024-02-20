package com.sparta.springprepare.service;

import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.dto.commentDto.CommentReqDto;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
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
    public List<CommentRespDto> findCommentOfPost(Long postId) {
        List<Comment> commentList = postRepository.findById(postId).get().getComments();
        List<CommentRespDto> resDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            resDtoList.add(new CommentRespDto(comment));
        }
        return resDtoList;
    }

    // 특정 게시판의 댓글 생성
    public CommentRespDto createCommentOfPost(Long postId, CommentReqDto dto, UserDetailsImpl userDetails) {

        Post findPost = postRepository.findById(postId).get();

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setPost(findPost);
        comment.setUser(userDetails.getUser());

        Comment savedComment = commentRepository.save(comment);

        return new CommentRespDto(savedComment);
    }

    @Transactional
    public CommentRespDto updateCommentOfPost(Long commentId, CommentReqDto dto, UserDetailsImpl userDetails) {
        Comment findComment = commentRepository.findById(commentId).get();
        if(!Objects.equals(findComment.getUser().getUsername(), userDetails.getUser().getUsername())) {
            throw new IllegalArgumentException("등록한 댓글이 아닙니다.");
        }
        findComment.setContent(dto.getContent());
        Comment savedComment = commentRepository.save(findComment);
        return new CommentRespDto(savedComment);
    }

    public CommentRespDto deleteCommentOfPost(Long commentId, UserDetailsImpl userDetails) {
        Comment findComment = commentRepository.findById(commentId).get();
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
        Comment findComment = checkComment(commentId);
        commentRepository.delete(findComment);
        return new CommentRespDto(findComment);
    }

    public CommentRespDto updateComment(CommentReqDto requestDto, Long commentId) {
        Comment findComment = checkComment(commentId);
        findComment.patch(requestDto);
        return new CommentRespDto(findComment);
    }

    private Comment checkComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }

}
