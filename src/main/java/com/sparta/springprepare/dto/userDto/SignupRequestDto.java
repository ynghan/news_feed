package com.sparta.springprepare.dto.userDto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Size(min = 4, max = 10, message = "패스워드는 4자 이상 10자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "사용자 이름은 알파벳 소문자와 숫자로 구성되어야 합니다.")
    private String username;

    @NotBlank
    @Size(min = 4, max = 10, message = "패스워드는 4자 이상 10자 이하로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W).*$", message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자로 구성되어야 합니다.")
    private String password;

    @Email
    @NotBlank
    private String email;


    private boolean admin = false;

    private String adminToken = "";
}
