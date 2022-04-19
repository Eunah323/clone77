package com.sparta.clone77.model;

import com.sparta.clone77.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class User{
    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToOne
    private Cart cart;

    @Formula("(select count(1) from orders ordercount where ordercount.user_id = id)")
    private int orderCount;

    @Column(unique = true)
    private Long kakaoId;


    public User(String username, String name, String password, String email, int orderCount) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.orderCount = orderCount;
        this.kakaoId = null;

    }

    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.kakaoId = null;

    }

    public User(String username, String password, String name, String email, Long kakaoId) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.kakaoId = kakaoId;

    }

}
