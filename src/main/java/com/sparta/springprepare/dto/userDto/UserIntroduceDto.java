package com.sparta.springprepare.dto.userDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIntroduceDto {
    String introduce;
    public UserIntroduceDto(String introduce) {
        this.introduce = introduce;
    }
}
