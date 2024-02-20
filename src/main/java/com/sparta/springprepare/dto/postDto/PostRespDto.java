package com.sparta.springprepare.dto.postDto;

import com.sparta.springprepare.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRespDto {

    private Long postId;
    private String content;
    private Long userId;

    public PostRespDto(Post post) {
        this.postId = post.getId();
        this.content = post.getContent();
        this.userId = post.getUser().getId();
    }
}
