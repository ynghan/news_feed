package com.sparta.springprepare.service;

import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.PostLike;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.PostLikeDto;
import com.sparta.springprepare.repository.PostLikeRepository;
import com.sparta.springprepare.repository.PostRepository;
import com.sparta.springprepare.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    public PostLikeService(PostRepository postRepository, PostLikeRepository postLikeRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.userRepository = userRepository;
    }

    // 로그인 사용자가 특정 게시물에 좋아요
    public PostLikeDto onPostLike(Long postId, User loginUser) {

        Post post = postRepository.findById(postId).get();
        User user = userRepository.findByUsername(loginUser.getUsername()).get();

        PostLike savedPostLike = postLikeRepository.save(new PostLike(post, user));

        return new PostLikeDto(savedPostLike);
    }
}
