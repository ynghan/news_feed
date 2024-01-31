package com.sparta.springprepare.dto;

import com.sparta.springprepare.domain.UserRoleEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserRequestDto {
    @NotEmpty
    @Size(min = 4, max = 10, message = "username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.")
    private String username;

    @NotEmpty
    @Size(min = 8, max = 15, message = "password는 최소 8자 이상, 15자 이하이 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 합니다.")
    private String password;
    private String email;
    private UserRoleEnum userRoleEnum;
}
