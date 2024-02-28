package com.sparta.springprepare.dto.userDto;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.domain.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class UserReqDto {

    @Getter
    @Setter
    public static class LoginReqDto {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class JoinReqDto {
        @NotEmpty
        @Pattern(regexp="^[a-z0-9]{4,10}$", message = "username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.")
        private String username;

        @NotEmpty
        @Size(min = 4, max = 10, message = "패스워드는 최소 4자 이상, 10자 이하이 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 합니다.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W).*$", message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자로 구성되어야 합니다.")
        private String password;

        @NotEmpty
        @Pattern(regexp="^[a-zA-Z0-9]{2,8}@[a-zA-Z0-9]{1,6}\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 작성해주세요")
        private String email;

        private boolean admin = false; // 모든 회원가입은 false

        private String adminToken = "";

        public User toEntity(PasswordEncoder passwordEncoder) {
            User.UserBuilder userBuilder = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .role(admin ? UserRoleEnum.ADMIN : UserRoleEnum.USER) // 관리자인 경우 role을 ADMIN으로 설정
                    .createAt(LocalDateTime.now());

            return userBuilder.build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PasswordReqDto {

        @NotBlank
        @Size(min = 4, max = 10, message = "패스워드는 4자 이상 10자 이하로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W).*$", message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자로 구성되어야 합니다.")
        private String newPassword;

        @NotBlank
        private String confirmNewPassword;
    }

}
