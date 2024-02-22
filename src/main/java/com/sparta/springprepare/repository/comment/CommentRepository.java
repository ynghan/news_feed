package com.sparta.springprepare.repository.comment;

import com.sparta.springprepare.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>, CommentRepositoryCustom {

}
