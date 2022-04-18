package com.sparta.clone77.dto;

import com.sparta.clone77.model.Option;
import com.sparta.clone77.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private Long productId;
    private boolean productType = false;
    private int price;
    private String serving;
    private String imageFile;
    private String name;
    private List<String> optionList = new ArrayList<>();

    public ProductResponseDto(Product product){
        this.productId = product.getId();
        if (product.getName().contains("무항생제")){ this.productType = true; }
        this.price = product.getPrice();
        this.serving = product.getServing();
        this.imageFile = product.getImage();
        this.name = product.getName();

        for ( Option option : product.getOptions()){
            this.optionList.add(option.getName());
        }

    }

}
