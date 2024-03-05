package com.sparta.springprepare.dto.userDto;


import com.sparta.springprepare.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserIntroduceDto {

    String introduce;

    public UserIntroduceDto(String introduce) {
        this.introduce = introduce;
    }

    public UserIntroduceDto(User user) {
        this.introduce = user.getIntroduce();
    }
}
