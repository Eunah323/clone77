package com.sparta.clone77.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.clone77.dto.KakaoUserInfoDto;
import com.sparta.clone77.dto.LoginResponseDto;
import com.sparta.clone77.dto.UserRequestDto;
import com.sparta.clone77.model.User;
import com.sparta.clone77.security.UserDetailsImpl;
import com.sparta.clone77.service.KakaoUserService;
import com.sparta.clone77.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final KakaoUserService kakaoUserService;



    //회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<String> createUser(@RequestBody UserRequestDto requestDto) {
        userService.registerUser(requestDto);
        return ResponseEntity.ok().body("회원가입 성공");

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
        String name = userDetails.getName();
        return new LoginResponseDto(is_login,username, name);
    }

    @GetMapping("/user/kakao/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드

        return kakaoUserService.kakaoLogin(code);
    }

}
