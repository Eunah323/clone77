package com.sparta.clone77.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<ProductTypeDto> productType;

}
