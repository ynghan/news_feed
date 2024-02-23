package com.sparta.springprepare.service.comment;

import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.dto.commentDto.CommentReqDto;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
import com.sparta.springprepare.repository.comment.CommentRepository;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.util.EntityCheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EntityCheckUtil entityCheckUtil;

    // 특정 게시판의 댓글 목록 조회하기
    // Post postPS = entityCheckUtil.checkPost(postId);
    // Page<Comment> commentList = commetRepository.findByPost(postPS);
    // 리팩터링하기
    @Transactional
    public List<CommentRespDto> findCommentOfPost(Long postId) {
        List<Comment> commentList = entityCheckUtil.checkPost(postId).getComments();
        List<CommentRespDto> resDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            resDtoList.add(new CommentRespDto(comment));
        }
        return resDtoList;
    }

    // 특정 게시판의 댓글 생성
    public CommentRespDto createCommentOfPost(Long postId, CommentReqDto dto, UserDetailsImpl userDetails) {

        Post findPost = entityCheckUtil.checkPost(postId);

        Comment comment = new Comment();
        comment.patch(dto, findPost, userDetails.getUser());

        Comment savedComment = commentRepository.save(comment);

        return new CommentRespDto(savedComment);
    }

    @Transactional
    public CommentRespDto updateCommentOfPost(Long commentId, CommentReqDto dto, UserDetailsImpl userDetails) {
        Comment findComment = entityCheckUtil.checkCommentById(commentId);
        if(!Objects.equals(findComment.getUser().getUsername(), userDetails.getUser().getUsername())) {
            throw new IllegalArgumentException("등록한 댓글이 아닙니다.");
        }
        findComment.setContent(dto.getContent());
        Comment savedComment = commentRepository.save(findComment);
        return new CommentRespDto(savedComment);
    }

    public CommentRespDto deleteCommentOfPost(Long commentId, UserDetailsImpl userDetails) {
        Comment findComment = entityCheckUtil.checkCommentById(commentId);
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
        Comment findComment = entityCheckUtil.checkCommentById(commentId);
        commentRepository.delete(findComment);
        return new CommentRespDto(findComment);
    }

    public CommentRespDto updateComment(CommentReqDto requestDto, Long commentId) {
        Comment findComment = entityCheckUtil.checkCommentById(commentId);
        findComment.setContent(requestDto.getContent());
        return new CommentRespDto(findComment);
    }


}
