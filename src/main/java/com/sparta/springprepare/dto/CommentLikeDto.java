package com.sparta.springprepare.dto;

import com.sparta.springprepare.domain.CommentLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentLikeDto {

    private Long postId;
    private Long userId;

    public CommentLikeDto(CommentLike commentLike) {
        this.postId = commentLike.getComment().getId();
        this.userId = commentLike.getUser().getId();
    }

}