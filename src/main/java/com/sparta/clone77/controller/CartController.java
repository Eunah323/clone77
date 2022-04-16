package com.sparta.clone77.controller;

import com.sparta.clone77.security.UserDetailsImpl;
import com.sparta.clone77.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

//    @Autowired
//    CartService cartService;
//
//    @GetMapping("/cart")
//    public ResponseDto readCart(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        return cartService.getCart(userDetails);
//    }
//
//    // 카트 전체 목록 삭제
//    @DeleteMapping("/cart")
//    public StatusDto deleteCarts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return cartService.delCart(userDetails);
//    }
//
//    // 카트 목록 개별 삭제
//    @DeleteMapping("/cart/{id}")
//    public StatusDto deleteCart(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return cartService.delCart(id, userDetails);
//    }

}
