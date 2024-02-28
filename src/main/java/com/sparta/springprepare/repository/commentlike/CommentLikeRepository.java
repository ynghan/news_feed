package com.sparta.springprepare.repository.commentlike;

import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.CommentLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>, CommentLikeRepositoryCustom {

    @Override
    Page<CommentLike> findByComment(Comment comment, Pageable pageable);

}
