package com.sparta.springprepare.dto.postDto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReqDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
