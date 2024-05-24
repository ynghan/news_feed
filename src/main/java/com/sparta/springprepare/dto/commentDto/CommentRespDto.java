package com.sparta.springprepare.dto.commentDto;

import com.sparta.springprepare.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRespDto {

    private Long commentId;
    private Long userId;
    private Long postId;
    private String content;
    private int likeCount;

    public CommentRespDto(Comment comment) {
        this.commentId = comment.getId();
        this.userId = comment.getUser().getId();
        this.content = comment.getContent();
        this.postId = comment.getPost().getId();
        this.likeCount = comment.getCommentLikes().size();
    }
}
