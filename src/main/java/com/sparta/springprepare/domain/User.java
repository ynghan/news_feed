package com.sparta.springprepare.domain;

import com.sparta.springprepare.dto.userDto.UserInfoDto;
import com.sparta.springprepare.dto.userDto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 프로필 사진
    private String profile;

    // aws s3 이미지 url
    private String photoImage;

    // 한줄 소개
    private String introduce;

    // 인적 정보
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;



    @Column(nullable = false, unique = true)
    private String email;
    private String nickname;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    // 히스토리
    private LocalDateTime created_date;
//    private int age;
//    private String gender;
//    private String country;
//    private String phone_num;

    // 계정 정보
//    private String identity;

    /**
     * 연관 관계 매핑
     * User : Follow = 1 : N
     * User : Post = 1 : N
     */
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "followee")
    private List<Follow> followees = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PasswordHistory> passHis = new ArrayList<>();


    //Dto 매핑
    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.email = requestDto.getEmail();
        this.role = requestDto.getUserRoleEnum();
    }

    public void changeProfile(String profile) {
        this.profile = profile;
    }

    public void patch(UserInfoDto dto) {
        // 예외 발생
        if (dto.getUsername() != null)
            this.username = dto.getUsername();
        // 객체를 갱신
        if (dto.getNickname() != null)
            this.nickname = dto.getNickname();
        if (dto.getIntroduce() != null)
            this.introduce = dto.getIntroduce();
        if (dto.getRole() != null)
            this.role = dto.getRole();
    }

}
