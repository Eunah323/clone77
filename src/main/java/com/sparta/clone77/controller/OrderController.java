package com.sparta.clone77.controller;

import com.sparta.clone77.security.UserDetailsImpl;
import com.sparta.clone77.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

//    @Autowired
//    OrderService orderService;
//
//    @PostMapping("/orders")
//    public StatusDto postOrder(OrderRequestDto requestDto,
//                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return orderService.postOrder(requestDto, userDetails);
//    }

}
