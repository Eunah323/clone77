package com.sparta.clone77.controller;

import com.sparta.clone77.dto.*;
import com.sparta.clone77.security.UserDetailsImpl;
import com.sparta.clone77.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    // 장바구니 생성
    @PostMapping("/cart")
    public StatusDto loadCart(@RequestBody CartRequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.addCart(requestDto, userDetails);
    }

    // 장바구니 조회
    @GetMapping("/cart")
    public CartResponseDto readCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.getCart(userDetails);
    }

    // 장바구니 날리기(주문완료)
    @DeleteMapping("/orders")
    public StatusDto orderCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.orderCart(userDetails);
    }

    // 장바구니 수량 변경
    @PutMapping("/cart")
    public StatusDto updateCart(@RequestBody CartUpdateReqeustDto requestDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.putCart(requestDto, userDetails);
    }

    // 장바구니 개별 삭제
    @PutMapping ("/cart/{productId}")
    public StatusDto delCart(@PathVariable Long productId,
                             @RequestBody OptionDto option,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return cartService.delcart(productId, option, userDetails);
    }
}
