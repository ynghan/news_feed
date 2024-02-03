package com.sparta.springprepare.dto;


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
