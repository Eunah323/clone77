package com.sparta.clone77.dto;

import com.sparta.clone77.model.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CartItemDto {

    private Long productId;
    private String name;
    private String image;
    private String option;
    private int quantity;
    private int price;
    private String serving;
    private ProductTypeDto productType;

    public CartItemDto(CartItem item){
        this.productId = item.getProduct().getId();
        this.name = item.getProduct().getName();
        this.image = item.getProduct().getImage();
        this.quantity = item.getQuantity();
        this.price = item.getProduct().getPrice();
        this.serving = item.getProduct().getServing();
        this.option = item.getOption();
        if ( this.name.contains("초신선") ) { productType.setFresh(true); }
        if ( this.name.contains("무항생") ) { productType.setZero(true); }
    }

}
