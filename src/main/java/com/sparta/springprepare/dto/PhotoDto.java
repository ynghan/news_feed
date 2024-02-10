package com.sparta.springprepare.dto;

import com.sparta.springprepare.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoDto {

    private Long userId;
    private String url;

    public PhotoDto(User savedUser) {
        this.userId = savedUser.getId();
        this.url = savedUser.getPhotoImage();
    }
}
