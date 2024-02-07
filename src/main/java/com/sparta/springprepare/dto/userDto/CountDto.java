package com.sparta.springprepare.dto.userDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountDto {
    public CountDto(long count) {
        this.count = count;
    }
    private long count;
}
