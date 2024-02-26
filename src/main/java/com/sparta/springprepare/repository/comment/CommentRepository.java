package com.sparta.springprepare.repository.comment;

import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long>, CommentRepositoryCustom {

    @Override
    Optional<Comment> findById(Long id);

    @Override
    Page<Comment> findByPost(Post post, Pageable pageable);
}
