package com.sparta.clone77.controller;

import com.sparta.clone77.dto.CartRequestDto;
import com.sparta.clone77.dto.CartResponseDto;
import com.sparta.clone77.dto.StatusDto;
import com.sparta.clone77.security.UserDetailsImpl;
import com.sparta.clone77.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    // 장바구니 생성
    @PostMapping("/cart")
    public ResponseEntity<StatusDto> loadCart(@RequestBody CartRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(
                "application",
                "json",
                StandardCharsets.UTF_8));

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(cartService.addCart(requestDto, userDetails));
    }

    // 장바구니 조회
    @GetMapping("/cart")
    public CartResponseDto readCart(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return cartService.getCart(userDetails);
    }

    // 장바구니 날리기(주문완료)
    @DeleteMapping("/cart")
    public StatusDto delCart(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return cartService.delCart(userDetails);
    }

}
