package com.sparta.springprepare.dto.userDto;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
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
    UserRoleEnum role;
    String introduce;
    String nickname;

    public UserInfoDto(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
        this.introduce = user.getIntroduce();
        this.nickname = user.getNickname();
    }

    public UserInfoDto(String introduce) {
        this.username = null;
        this.introduce = introduce;
        this.nickname = null;
        this.role = null;
    }
}
