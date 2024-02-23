package com.sparta.springprepare.dto;

import com.sparta.springprepare.domain.Follow;
import com.sparta.springprepare.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {

    private String username;

    public FollowDto(User findFollowUser) {
        this.username = findFollowUser.getUsername();
    }

    public FollowDto(Follow follow) {
        this.username = follow.getFollower().getUsername();
    }
}
