package com.sparta.clone77.model;

import com.sparta.clone77.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false) // nullable 설정은 추후 논의가 필요합니다.
    private String email;

    @Column(nullable = false)
    private int orderCount;

    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
    }

}
