package com.sparta.springprepare.service.postlike;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.PostLikeDto;
import org.springframework.data.domain.Pageable;

public interface PostLikeService {

    // 로그인 사용자가 특정 게시물에 좋아요
    PostLikeDto onPostLike(Long postId, User user);

    PostLikeDto deleteLikeToPost(Long postId, User user, Pageable pageable);
}