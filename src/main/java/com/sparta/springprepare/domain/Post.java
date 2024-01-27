package com.sparta.springprepare.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    /**
     * 연관 관계 매핑
     * Post : Feed = N : 1
     * Post : Comment = 1 : N
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

}
