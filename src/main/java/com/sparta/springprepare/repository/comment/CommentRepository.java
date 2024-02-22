package com.sparta.springprepare.repository.comment;

import com.sparta.springprepare.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long>, CommentRepositoryCustom {

    @Override
    Optional<Comment> findById(Long id);
}
