package com.sparta.clone77.dto;

import com.sparta.clone77.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private Long productId;
    private boolean productType = false;
    private int price;
    private String serving;
    private String name;
    private List<String> imageFile = new ArrayList<>();
    private List<String> optionList = new ArrayList<>();

    public ProductResponseDto(Product product){
        this.productId = product.getId();
        if (product.getName().contains("무항생제")){ this.productType = true; }
        this.price = product.getPrice();
        this.serving = product.getServing();
        this.name = product.getName();
        // product.getSelector와 image를 배열로 만들기
        this.imageFile.addAll(Arrays.asList(product.getImage().split(";")));
        this.optionList.addAll(Arrays.asList(product.getSelector().split(";")));
    }

}
