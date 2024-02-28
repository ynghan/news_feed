package com.sparta.springprepare.service.commetlike;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.CommentLikeDto;
import org.springframework.data.domain.Pageable;

public interface CommentLikeService {

    CommentLikeDto pushCommentLike(Long commentId, User user);

    void deleteCommentLike(Long commentId, User user, Pageable pageable);
}
