package com.sparta.springprepare.service.postlike.Impl;

import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.PostLike;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.PostLikeDto;
import com.sparta.springprepare.handler.ex.CustomApiException;
import com.sparta.springprepare.handler.ex.ErrorCode;
import com.sparta.springprepare.repository.post.PostRepository;
import com.sparta.springprepare.repository.postlike.PostLikeRepository;
import com.sparta.springprepare.repository.user.UserRepository;
import com.sparta.springprepare.service.postlike.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 로그인 사용자가 특정 게시물에 좋아요
    @Override
    public PostLikeDto onPostLike(Long postId, User loginUser) {

        Post postPS = checkPost(postId);
        User userPS = checkUserByUsername(loginUser.getUsername());

        if(postPS.getUser().getId().equals(userPS.getId())) {
            throw new CustomApiException(ErrorCode.SELF_POSTLIKE_NOT_ALLOWED);
        }

        PostLike postLikePS = postLikeRepository.save(new PostLike(postPS, userPS));

        return new PostLikeDto(postLikePS);
    }

    @Override
    public PostLikeDto deleteLikeToPost(Long postId, User user, Pageable pageable) {
        // 게시물 확인
        Post postPS = checkPost(postId);

        // 해당 게시물의 좋아요 리스트 찾기
        Page<PostLike> postLikes =  postLikeRepository.findByPostId(postPS.getId(), pageable);

        // 해당 좋아요 중에서 사용자 id를 가지는 좋아요 찾기
        PostLike userLike = postLikes.stream()
                .filter(like -> like.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new CustomApiException(ErrorCode.POSTLIKE_NOT_EXIST));

        // 좋아요 삭제
        postLikeRepository.deleteById(userLike.getId());

        return new PostLikeDto(userLike);
    }


    private User checkUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_EXIST));
    }

    private Post checkPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomApiException(ErrorCode.POST_NOT_EXIST));
    }
}
