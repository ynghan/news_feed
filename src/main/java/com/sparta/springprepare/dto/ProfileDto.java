package com.sparta.springprepare.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDto {

    private String profile;

    public ProfileDto(String profile) {
        this.profile = profile;
    }
}
