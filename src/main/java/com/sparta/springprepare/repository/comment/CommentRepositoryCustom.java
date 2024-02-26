package com.sparta.springprepare.repository.comment;

import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentRepositoryCustom {

    Optional<Comment> findById(Long id);

    Page<Comment> findByPost(Post post, Pageable pageable);
}
