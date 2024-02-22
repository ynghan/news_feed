package com.sparta.springprepare.api;

import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.domain.PostLike;
import com.sparta.springprepare.dto.PostLikeDto;
import com.sparta.springprepare.repository.PostLikeRepository;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.util.EntityCheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostLikeApiController {

    private final PostLikeRepository postLikeRepository;
    private final EntityCheckUtil entityCheckUtil;


    // 로그인 사용자가 특정 게시물에 좋아요를 남긴다. 본인이 작성한 게시물에는 좋아요를 남길 수 없다.
    @PostMapping("/like/post/{postId}")
    public PostLikeDto onPostLike(@PathVariable(name="postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Post findPost = entityCheckUtil.checkPost(postId);

        if(findPost.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("자신의 게시물에는 좋아요를 누를 수 없습니다.");
        }

        PostLike postLike = new PostLike(findPost, userDetails.getUser());
        postLikeRepository.save(postLike);
        return new PostLikeDto(postLike);
    }

    // 로그인 사용자는 특정 게시물에 좋아요를 취소할 수 있어야 한다.
    @DeleteMapping("/like/post/{postId}")
    public PostLikeDto deleteLikeToPost(@PathVariable(name="postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostLike findPostLike = postLikeRepository.findAll().stream().filter(pl -> pl.getPost().getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 누른 게시물이 없습니다."));
        postLikeRepository.delete(findPostLike);
        return new PostLikeDto(findPostLike);
    }

}
