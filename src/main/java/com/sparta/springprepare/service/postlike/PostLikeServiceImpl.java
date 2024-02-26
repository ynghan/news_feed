package com.sparta.springprepare.service.postlike;

import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.PostLike;
import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.dto.PostLikeDto;
import com.sparta.springprepare.repository.post.PostRepository;
import com.sparta.springprepare.repository.postlike.PostLikeRepository;
import com.sparta.springprepare.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
            throw new IllegalArgumentException("자신의 게시물에는 좋아요를 누를 수 없습니다.");
        }

        PostLike postLikePS = postLikeRepository.save(new PostLike(postPS, userPS));

        return new PostLikeDto(postLikePS);
    }

    @Override
    public PostLikeDto deleteLikeToPost(Long postId, User user, org.springframework.data.domain.Pageable pageable) {
        // 게시물 확인
        Post postPS = checkPost(postId);

        // 해당 게시물의 좋아요 리스트 찾기
        Page<PostLike> postLikes =  postLikeRepository.findByPostId(postPS.getId(), pageable);

        // 해당 좋아요 중에서 사용자 id를 가지는 좋아요 찾기
        PostLike userLike = postLikes.stream()
                .filter(like -> like.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 좋아요가 존재하지 않습니다."));

        // 좋아요 삭제
        postLikeRepository.deleteById(userLike.getId());

        return new PostLikeDto(userLike);
    }


    private User checkUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    private Post checkPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }
}
