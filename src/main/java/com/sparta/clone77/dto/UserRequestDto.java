package com.sparta.clone77.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDto {
    private String username;
    private String password;
    private String passwordCheck;
    private String name;

    public UserRequestDto(String username, String password, String passwordCheck, String name) {
        this.username = username;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.name = name;
    }
}

