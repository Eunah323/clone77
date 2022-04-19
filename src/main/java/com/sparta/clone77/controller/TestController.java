package com.sparta.clone77.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.clone77.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    private final UserService userService;

    @Autowired
    public TestController(UserService userService) {
        this.userService = userService;
    }


    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {

        return "login";
    }
    @GetMapping("/")
    public String home() {

        return "index";
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        userService.kakaoLogin(code);

        return "redirect:/";
    }


}
