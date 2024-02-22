package com.sparta.springprepare.service;

import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.PostLike;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.PostLikeDto;
import com.sparta.springprepare.repository.PostLikeRepository;
import com.sparta.springprepare.util.EntityCheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final EntityCheckUtil entityCheckUtil;

    // 로그인 사용자가 특정 게시물에 좋아요
    public PostLikeDto onPostLike(Long postId, User loginUser) {

        Post post = entityCheckUtil.checkPost(postId);
        User user = entityCheckUtil.checkUserByUsername(loginUser.getUsername());

        PostLike savedPostLike = postLikeRepository.save(new PostLike(post, user));

        return new PostLikeDto(savedPostLike);
    }
}
