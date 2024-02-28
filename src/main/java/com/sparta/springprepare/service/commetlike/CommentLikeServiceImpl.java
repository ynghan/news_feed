package com.sparta.springprepare.service.commetlike;

import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.CommentLike;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.CommentLikeDto;
import com.sparta.springprepare.handler.ex.CustomApiException;
import com.sparta.springprepare.handler.ex.ErrorCode;
import com.sparta.springprepare.repository.comment.CommentRepository;
import com.sparta.springprepare.repository.commentlike.CommentLikeRepository;
import com.sparta.springprepare.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public CommentLikeDto pushCommentLike(Long commentId, User user) {
        Comment findComment = checkCommentById(commentId);

        if(findComment.getUser().equals(user)) {
            throw new CustomApiException(ErrorCode.SELF_COMMENTLIKE_NOT_ALLOWED);
        }

        CommentLike commentLike = new CommentLike(findComment, user);
        commentLikeRepository.save(commentLike);
        return new CommentLikeDto(commentLike);
    }

    @Override
    public void deleteCommentLike(Long commentId, User user, Pageable pageable) {

        User userPS = checkUserById(user.getId());
        Comment comment = checkCommentById(commentId);
        Page<CommentLike> commentLikes = commentLikeRepository.findByComment(comment, pageable);

        boolean isCommentLikeExist = false;

        for (CommentLike commentLike : commentLikes) {
            if(commentLike.getUser().getId().equals(userPS.getId())) {
                isCommentLikeExist = true;
                commentLikeRepository.deleteById(commentLike.getId());
            }
        }

        if (!isCommentLikeExist) {
            throw new CustomApiException(ErrorCode.CANNOT_UNLIKE);
        }
    }

    private Comment checkCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CustomApiException(ErrorCode.COMMENT_NOT_EXIST));
    }

    private User checkUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_EXIST));
    }
}
