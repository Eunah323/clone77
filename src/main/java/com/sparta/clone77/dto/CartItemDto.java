package com.sparta.clone77.dto;

import com.sparta.clone77.model.CartItem;
import com.sparta.clone77.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

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
    private Map<String, Boolean> productType = new HashMap<>();

    public CartItemDto(CartItem item, Product product){
        this.productId = product.getId();
        this.name = product.getName();
        this.image = product.getImage();
        this.quantity = item.getQuantity();
        this.price = product.getPrice();
        this.serving = product.getServing();
        this.option = item.getOption();

        if ( this.name.contains("초신선") ) { productType.put("fresh",true); }
        else { productType.put("fresh",false); }
        if ( this.name.contains("무항생") ) { productType.put("zero",true); }
        else { productType.put("zero",false); }
    }

}
