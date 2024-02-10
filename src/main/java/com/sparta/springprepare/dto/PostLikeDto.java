package com.sparta.springprepare.dto;


import com.sparta.springprepare.domain.PostLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLikeDto {
    private Long postId;
    private Long userId;

    public PostLikeDto(PostLike postLike) {
        this.postId = postLike.getPost().getId();
        this.userId = postLike.getUser().getId();
    }
}
