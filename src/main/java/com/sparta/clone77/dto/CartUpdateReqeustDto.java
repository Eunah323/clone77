package com.sparta.clone77.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartUpdateReqeustDto {

    private Long productId;
    private boolean operator;

}
