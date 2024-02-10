package com.sparta.springprepare.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    /**
     * 연관 관계 매핑
     * Follow : User = N : 1
     */

    // 팔로우 신청한 사람
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "follower_id") // 외래키로 동작, name : 외래키의 컬럼명을 지정함
    private User follower;

    // 팔로우 신청받은 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id")
    private User followee;

    public Follow(User loginUser, User findFollowUser) {
        this.follower = findFollowUser; // 팔로우되는 사람
        this.followee = loginUser; // 팔로우 신청하는 사람
    }
}
