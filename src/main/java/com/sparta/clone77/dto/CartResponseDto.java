package com.sparta.clone77.dto;

import com.sparta.clone77.model.Cart;
import com.sparta.clone77.model.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CartResponseDto {

    private int orderCount;
    private List<CartItemDto> lists = new ArrayList<>();

    public CartResponseDto(Cart cart){
        this.orderCount = cart.getUser().getOrderCount();

        for (CartItem item : cart.getCartItems()){
            lists.add(new CartItemDto(item));
        }
    }
}
