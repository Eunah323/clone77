package com.sparta.clone77.controller;

import com.sparta.clone77.dto.LoginResponseDto;
import com.sparta.clone77.dto.UserRequestDto;
import com.sparta.clone77.dto.UserResponseDto;
import com.sparta.clone77.model.User;
import com.sparta.clone77.repository.UserRepository;
import com.sparta.clone77.security.UserDetailsImpl;
import com.sparta.clone77.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;


    //회원가입
    @PostMapping("/user/signup")
    public User createUser(@RequestBody UserRequestDto requestDto) {

        return userService.registerUser(requestDto);
    }

    // 예외 처리
    @ExceptionHandler({IllegalArgumentException.class})
    public Map<String, String> handleException(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("errMsg", e.getMessage());
        return map;
    }

    //로그인후 유저정보 가져오기
    @PostMapping("/user/loginCheck")
    public LoginResponseDto UserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){

        String is_login = "true";
        String username = userDetails.getUsername();
        String nickname = userDetails.getName();
        return new LoginResponseDto(is_login,username, nickname);
    }


}
