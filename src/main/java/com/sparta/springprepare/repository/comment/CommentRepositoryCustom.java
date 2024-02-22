package com.sparta.springprepare.repository.comment;

import com.sparta.springprepare.domain.Comment;

import java.util.Optional;

public interface CommentRepositoryCustom {

    Optional<Comment> findById(Long id);
}
