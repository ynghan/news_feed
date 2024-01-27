package com.sparta.springprepare.controller;

import com.sparta.springprepare.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {


    // 회원가입 페이지
    @PostMapping("/signup")
    public ResponseEntity<String>  registerUser(@Valid @RequestBody UserDto userDto) {
        userService.resgisterUser(userDto);
        return new ResponseEntity<>("회원가입이 성공적으로 완료되었습니다.", HttpStatus.CREATED);
    }

    //로그인 페이지
    @PostMapping("/login")
    public ResponseEntity<String> postLogin(@RequestBody UserDto userDto) {
        String token = authService.login(userDto.getUsername(), userDto.getPassword());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>("로그인에 성공하였습니다.", headers, HttpStatus.OK);
    }

    

}
