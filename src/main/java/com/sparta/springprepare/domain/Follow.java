package com.sparta.springprepare.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    /**
     * 연관 관계 매핑
     * Follow : User = N : 1
     */
    // 내가 팔로우 한 사람
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "follower_id") // 외래키로 동작, name : 외래키의 컬럼명을 지정함
    private User follower;

    // 나를 팔로우 한 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id")
    private User followee;

}
