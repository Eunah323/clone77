package com.sparta.clone77.service;


import com.sparta.clone77.dto.UserRequestDto;
import com.sparta.clone77.model.User;
import com.sparta.clone77.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
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



}
