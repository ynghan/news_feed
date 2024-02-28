package com.sparta.springprepare.dto.userDto;

import com.sparta.springprepare.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    String username;
    String introduce;
    String nickname;

    public UserInfoDto(User user) {
        this.username = user.getUsername();
        this.introduce = user.getIntroduce();
        this.nickname = user.getNickname();
    }
}
