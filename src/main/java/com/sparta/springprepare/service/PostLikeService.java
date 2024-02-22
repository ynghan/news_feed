package com.sparta.springprepare.service;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.PostLikeDto;

public interface PostLikeService {

    // 로그인 사용자가 특정 게시물에 좋아요
    PostLikeDto onPostLike(Long postId, User loginUser);
}