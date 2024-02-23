package com.sparta.springprepare.service.commetlike;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.CommentLikeDto;

public interface CommentLikeService {

    CommentLikeDto pushLikeToComment(Long commentId, User user);

    CommentLikeDto deleteLikeToComment(Long commentId, User user);
}
