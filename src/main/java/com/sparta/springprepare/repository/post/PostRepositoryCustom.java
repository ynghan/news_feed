package com.sparta.springprepare.repository.post;

import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.User;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findAllByUser(User user);
}
