package com.sparta.springprepare.repository.post;

import com.sparta.springprepare.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRedisRepository extends CrudRepository<Post, String> {
}
