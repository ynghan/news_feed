package com.sparta.springprepare.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileEncodingDto {

    private String profile;

    public ProfileEncodingDto(String profile) {
        this.profile = profile;
    }
}
