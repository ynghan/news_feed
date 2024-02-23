package com.sparta.springprepare.service.commetlike;

import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.CommentLike;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.CommentLikeDto;
import com.sparta.springprepare.repository.CommentLikeRepository;
import com.sparta.springprepare.util.EntityCheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final EntityCheckUtil entityCheckUtil;

    @Override
    public CommentLikeDto pushLikeToComment(Long commentId, User user) {
        Comment findComment = entityCheckUtil.checkCommentById(commentId);

        if(findComment.getUser().equals(user)) {
            throw new IllegalArgumentException("자신의 댓글에는 좋아요를 누를 수 없습니다.");
        }

        CommentLike commentLike = new CommentLike(findComment, user);
        commentLikeRepository.save(commentLike);
        return new CommentLikeDto(commentLike);
    }

    @Override
    public CommentLikeDto deleteLikeToComment(Long commentId, User user) {
        CommentLike findCommentLike = commentLikeRepository.findAll().stream().filter(cl -> cl.getComment().getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 누른 댓글이 없습니다."));

        commentLikeRepository.delete(findCommentLike);
        return new CommentLikeDto(findCommentLike);
    }
}
