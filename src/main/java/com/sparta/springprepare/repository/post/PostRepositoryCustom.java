package com.sparta.springprepare.repository.post;

import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> findAllByUser(User user, Pageable pageable);

    long countByUser(User user);

}
