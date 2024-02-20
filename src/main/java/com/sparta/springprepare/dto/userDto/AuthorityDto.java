package com.sparta.springprepare.dto.userDto;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthorityDto {

    private UserRoleEnum role;

    public AuthorityDto(User user) {
        this.role = user.getRole();
    }
}
