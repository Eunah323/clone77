package com.sparta.clone77.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtPreProcessingToken extends UsernamePasswordAuthenticationToken {

    private JwtPreProcessingToken(
            Object principal,
            Object credentials
    ) {
        super(
                principal,
                credentials
        );
    }

    public JwtPreProcessingToken(String token) {
        this(
                token,
                token.length()
        );
        System.out.println("프리프로세싱토큰");
    }
}
