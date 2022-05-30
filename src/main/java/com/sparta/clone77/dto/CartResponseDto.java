package com.sparta.clone77.dto;

import com.sparta.clone77.model.Cart;
import com.sparta.clone77.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CartResponseDto {

    private int orderCount;
    private List<CartItemDto> lists = new ArrayList<>();

    public CartResponseDto(Cart cart, List<Product> products, int orderCount){
        this.orderCount = orderCount;

        for ( int i = 0 ; i < cart.getCartItems().size() ; i ++ ) {
            lists.add(new CartItemDto(cart.getCartItems().get(i), products.get(i)));
        }
    }
}
