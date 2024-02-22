package com.sparta.springprepare.repository.post;


import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    List<Post> findAllByUser(User user);

}