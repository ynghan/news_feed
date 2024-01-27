package com.sparta.springprepare.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 인적 정보
    @Column(nullable = false, unique = true)
    private String username;

    private int age;
    private String gender;
    private String country;
    private String phone_num;

    // 계정 정보
    private String identity;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    private String nickname;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    // 히스토리
    private LocalDateTime created_date;

    /**
     * 연관 관계 매핑
     * User : Follow = 1 : N
     * User : Feed = 1 : 1
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @OneToMany(mappedBy = "follower")
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "followee")
    private List<Follow> followees = new ArrayList<>();



}
