package com.sparta.springprepare.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Feed {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    /**
     * 연관 관계 매핑
     * Feed : User = 1 : 1
     * Feed : Post = 1 : N
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "feed")
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy="feed")
    private List<Post> posts = new ArrayList<>();
}
