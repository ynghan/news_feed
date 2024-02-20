package com.sparta.springprepare.dto.userDto;

import com.sparta.springprepare.domain.User;
import com.sparta.springprepare.util.CustomDateUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class UserRespDto {

    @Getter
    @Setter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createdAt = CustomDateUtil.toStringFormat(user.getCreateAt());
        }
    }

    @ToString
    @Getter
    @Setter
    public static class JoinRespDto {
        // 유효성 검사
        private Long id;
        private String username;
        private String fullname;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }
    @Getter
    @Setter
    public static class GeneralRespDto {

        private Long id;
        private String username;
        private String password;

        public GeneralRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
        }
    }
}
