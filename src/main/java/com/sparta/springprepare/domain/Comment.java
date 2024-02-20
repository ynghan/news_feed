package com.sparta.springprepare.domain;

import com.sparta.springprepare.dto.commentDto.CommentReqDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    /**
     * 연관 관계 매핑
     * Comment : Post = N : 1
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void patch(CommentReqDto requestDto) {
        this.content = requestDto.getContent();
    }


}
