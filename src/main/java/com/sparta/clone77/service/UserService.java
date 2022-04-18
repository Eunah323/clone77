package com.sparta.clone77.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.clone77.dto.KakaoUserInfoDto;
import com.sparta.clone77.dto.UserRequestDto;
import com.sparta.clone77.model.User;
import com.sparta.clone77.repository.UserRepository;
import com.sparta.clone77.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입 및 유효성 검사
    public User registerUser(@RequestBody UserRequestDto requestDto) {

        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        String name = requestDto.getName();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);

        String pattern = "^[a-zA-Z0-9]*$";
        String pattern2 = "^[A-Za-z0-9#?!@$ %^&*-]*$";
//        ^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-])*$

        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디 입니다.");
        } else if (!Pattern.matches(pattern, username)) {
            throw new IllegalArgumentException("영문, 숫자로만 입력하세요");
        }  else if (!Pattern.matches(pattern2,password)) {
            throw new IllegalArgumentException("비밀변호는 대소문자숫자특수문자를 포함해야합니다");
        } else if (password.length() < 8) {
            throw new IllegalArgumentException("비밀번호를 8자 이상 입력하세요");
        } else if (password.contains(username)) {
            throw new IllegalArgumentException("비밀번호에 ID를 포함할 수 없습니다.");
        }   else if (!passwordCheck.equals(password)) {
            throw new IllegalArgumentException("비밀번호와 정확히 일치하게 작성해주세요");
        } else if (name.length() < 1) {
            throw new IllegalArgumentException("이름을 입력하세요");
        }
        // 패스워드 인코딩
        password = passwordEncoder.encode(password);
        requestDto.setPassword(password);



        User user = new User(requestDto);

        return userRepository.save(user);


    }
//    //카카오로그인관련
//    public void kakaoLogin(String code) throws JsonProcessingException {
//        String accessToken = getAccessToken(code);
//        // 1. "인가 코드"로 "액세스 토큰" 요청
//
//        // 2. 토큰으로 카카오 API 호출
//        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
//        // DB 에 중복된 Kakao Id 가 있는지 확인
//        Long kakaoId = kakaoUserInfo.getId();
//        User kakaoUser = userRepository.findByKakaoId(kakaoId)
//                .orElse(null);
//        if (kakaoUser == null) {
//// 회원가입
//// username: kakao nickname
//            String name = kakaoUserInfo.getName();
//
//// password: random UUID
//            String password = UUID.randomUUID().toString();
//            String encodedPassword = passwordEncoder.encode(password);
//
//
//
//            kakaoUser = new User(name, encodedPassword, kakaoId);
//            userRepository.save(kakaoUser);
//        }
//
//        // 4. 강제 로그인 처리
//        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
//        HttpHeaders headers = new HttpHeaders();
//
//        // HTTP Header 생성
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/json");
//
//// HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoUserInfoRequest,
//                String.class
//        );
//
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        Long id = jsonNode.get("id").asLong();
//        String name = jsonNode.get("properties")
//                .get("nickname").asText();
//
//
//        System.out.println("카카오 사용자 정보: " + id + ", " + name);
//        return new KakaoUserInfoDto(id, name);
//    }
//
//
//    private String getAccessToken(String code) throws JsonProcessingException {
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP Body 생성
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", "0aecdba9e77dedf60ad0601a2d95cd97");
//        body.add("redirect_uri", "http://3.39.23.124/user/kakao/callback");
//        body.add("code", code);
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(body, headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        return jsonNode.get("access_token").asText();
//
//    }


}
