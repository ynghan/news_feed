package com.sparta.springprepare.dto.postDto;

import com.sparta.springprepare.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class PostRespDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long postId;
    private String title;
    private String content;
    private Long userId;

    public PostRespDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUser().getId();
    }
}
