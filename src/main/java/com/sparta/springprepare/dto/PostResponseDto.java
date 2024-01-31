package com.sparta.springprepare.dto;

import com.sparta.springprepare.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {

    private String content;

    public PostResponseDto(Post post) {
        this.content = post.getContent();
    }
}
