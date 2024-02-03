package com.sparta.springprepare.dto;

import com.sparta.springprepare.domain.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDto {
    String username;
    UserRoleEnum role;
}
