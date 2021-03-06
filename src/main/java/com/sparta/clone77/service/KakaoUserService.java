package com.sparta.clone77.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.clone77.dto.KakaoUserInfoDto;

import com.sparta.clone77.model.User;
import com.sparta.clone77.repository.UserRepository;
import com.sparta.clone77.security.UserDetailsImpl;
import com.sparta.clone77.security.auth.FormLoginSuccessHandler;
import com.sparta.clone77.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
public class KakaoUserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public KakaoUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);
        System.out.println(accessToken);

        // 2. "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. "카카오 사용자 정보"로 필요시 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 4. 강제 로그인 처리
//        forceLogin(kakaoUser);
        // 4. 강제 로그인 처리
        String token= forceLogin(kakaoUser);

        HttpHeaders headers = new HttpHeaders();
        headers.set("token",token);

        return  ResponseEntity.ok()
                .headers(headers)
                .body("카카오로그인되었습니다.");
    }

    public String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "c4f01d9a0b79af30e7fa9225a1419b3b");
        body.add("redirect_uri", "http://localhost:3000/user/kakao/callback”");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    public KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String name = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        return new KakaoUserInfoDto(id, name, email);
    }
    public User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {
            // 회원가입
            // username: kakao nickname
            String name = kakaoUserInfo.getName();
            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            // email: kakao email
            String email = kakaoUserInfo.getEmail();
            // role: 일반 사용자

            kakaoUser = new User(name, name,encodedPassword, email, kakaoId);
            userRepository.save(kakaoUser);
        }

return kakaoUser;
    }

    public String forceLogin(User kakaoUser) {
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtTokenUtils.generateJwtToken(new UserDetailsImpl(kakaoUser));
//        ResponseEntity.ok().header(FormLoginSuccessHandler.AUTH_HEADER, FormLoginSuccessHandler.TOKEN_TYPE + " " + token);
        System.out.println(token);
        return token;

    }
//    public String getToken(KakaoUserInfoDto kakaoUserInfo){
//        String token = JWT.create()
//                .withSubject("JwtToken")
//                .withExpiresAt(new Date(System.currentTimeMillis()))
//                .withClaim("username", kakaoUserInfoDto.getName())
//                .withClaim("name", kakaoUserInfoDto.getName())
//                .withClaim("email", kakaoUserInfoDto.getEmail())
//                .sign(Algorithm.HMAC256("jwt_secret_!@#$%"));
//        ResponseEntity.ok().header(token);
//        System.out.println(token);
//
//        String token = JWT.create()
//                .withSubject("JwtToken")
//                .withClaim(JwtTokenUtils.CLAIM_EXPIRED_DATE, new Date(System.currentTimeMillis() + (1 * 60 * 60 * 24 * 3 * 1000)))
//                .withClaim("USER_NAME", kakaoUserInfo.getName())
//                .withClaim("NAME", kakaoUserInfo.getName())
//                .withClaim("EMAIL", kakaoUserInfo.getEmail())
//                .sign(Algorithm.HMAC256("jwt_secret_!@#$%"));
//        ResponseEntity.ok().header(token);
//        System.out.println(token);
//        return token;
//    }

}