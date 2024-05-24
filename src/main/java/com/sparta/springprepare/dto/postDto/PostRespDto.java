package com.sparta.springprepare.dto.postDto;

import com.sparta.springprepare.domain.Post;
import com.sparta.springprepare.dto.commentDto.CommentRespDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostDetailRespDto {

        private Long postId;
        private String title;
        private String content;
        private String username;
        private int postLikeCount;
        private List<CommentRespDto> commentRespDtos;
    }
}
