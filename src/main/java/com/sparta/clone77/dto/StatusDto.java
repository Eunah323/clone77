package com.sparta.clone77.dto;

import com.sparta.clone77.model.CartItem;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class StatusDto {

    private String message;

    public StatusDto(CartItem cartItem){
        this.message = "장바구니에 상품 추가 완료";
    }

    public StatusDto(List<Long> id){
        this.message = "주문 및 장바구니 삭제 완료";
    }
}
