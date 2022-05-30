package com.sparta.clone77.dto;

import lombok.Getter;

@Getter
public class CartRequestDto {

    private Long productId;
    private int quantity;
    private String option;

}
