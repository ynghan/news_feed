package com.sparta.springprepare.controller;


import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.CommentLike;
import com.sparta.springprepare.dto.CommentLikeDto;
import com.sparta.springprepare.repository.CommentLikeRepository;
import com.sparta.springprepare.repository.CommentRepository;
import com.sparta.springprepare.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentLikeController {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentLikeController(CommentRepository commentRepository, CommentLikeRepository commentLikeRepository) {
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
    }


    // 로그인 사용자가 댓글에 좋아요를 남긴다. 본인이 작성한 댓글에는 좋아요를 남길 수 없다.
    @PostMapping("/api/like/comment/{commentId}")
    public CommentLikeDto pushLikeToComment(@PathVariable(name="commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(findComment.getUser().equals(userDetails.getUser())) {
            throw new IllegalArgumentException("자신의 댓글에는 좋아요를 누를 수 없습니다.");
        }

        CommentLike commentLike = new CommentLike(findComment, userDetails.getUser());
        commentLikeRepository.save(commentLike);
        return new CommentLikeDto(commentLike);
    }


    // 로그인 사용자가 남긴 좋아요를 취소할 수 있어야 한다.
    @DeleteMapping("/api/like/comment/{commentId}")
    public CommentLikeDto deleteLikeToComment(@PathVariable(name="commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentLike findCommentLike = commentLikeRepository.findAll().stream().filter(cl -> cl.getComment().getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 누른 댓글이 없습니다."));

        commentLikeRepository.delete(findCommentLike);
        return new CommentLikeDto(findCommentLike);
    }

}
