package com.sparta.springprepare.repository.post;


import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Override
    Page<Post> findAllByUser(User user, Pageable pageable);

    Optional<Post> findByIdWithUser(Long postId);

//    @Query("SELECT COUNT(p) FROM Post p WHERE p.user = :user")
//    long countByUser(@Param("user") User user);

    long countByUser(User user);

}