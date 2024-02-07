package com.sparta.springprepare.repository;


import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);
    List<Post> findAllById(Long id);

}