package com.sparta.springprepare.repository.commentlike;

import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.CommentLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentLikeRepositoryCustom {

    Page<CommentLike> findByComment(Comment comment, Pageable pageable);
}
