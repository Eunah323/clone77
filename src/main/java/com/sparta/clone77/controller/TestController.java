//package com.sparta.clone77.controller;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.sparta.clone77.dto.KakaoUserInfoDto;
//import com.sparta.clone77.model.User;
//import com.sparta.clone77.repository.UserRepository;
//import com.sparta.clone77.security.UserDetailsImpl;
//import com.sparta.clone77.service.KakaoUserService;
//import com.sparta.clone77.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.Date;
//import java.util.UUID;
//
//@Controller
//public class TestController {
//
//    private final KakaoUserService kakaoUserService;
//
//
//    @Autowired
//    public TestController(KakaoUserService kakaoUserService) {
//        this.kakaoUserService = kakaoUserService;
//    }
//
//    // 회원 로그인 페이지
////    @GetMapping("/login")
////    public String login() {
////
////        return "login";
////    }
////    @GetMapping("/")
////    public String home() {
////
////        return "index";
////    }
//
//    @GetMapping("/user/kakao/callback")
//    @ResponseBody
//    public String kakaoLogin(UserDetailsImpl userDetails) throws JsonProcessingException {
//        // authorizedCode: 카카오 서버로부터 받은 인가 코드
//        String token = JWT.create()
//                .withSubject("JwtToken")
//                .withExpiresAt(new Date(System.currentTimeMillis()))
//                .withClaim("username", userDetails.getUsername())
//                .withClaim("name", userDetails.getName())
//                .withClaim("email", userDetails.getEmail())
//                .sign(Algorithm.HMAC256("jwt_secret_!@#$%"));
//        ResponseEntity.ok().header(token);
//        System.out.println(token);
//
//        return token;
//    }
//
//    @GetMapping("/user/test")
//    @ResponseBody
//    public String test( String code) throws JsonProcessingException{
//            // authorizedCode: 카카오 서버로부터 받은 인가 코드
//kakaoUserService.kakaoLogin(code);
//            return code;
//        }
//
//
//}
