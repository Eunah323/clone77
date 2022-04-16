package com.sparta.clone77.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartResponseDto {

    private int orderCount;
    private List<CartItemDto> cartItemDtos;

}
