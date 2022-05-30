package com.sparta.clone77.dto;

import com.sparta.clone77.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserResponseDto extends User {
    String status;
    String http;
    String msg;

    public UserResponseDto(UserRequestDto requestDto) {
    }
}
