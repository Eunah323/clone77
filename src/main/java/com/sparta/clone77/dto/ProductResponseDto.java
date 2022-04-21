package com.sparta.clone77.dto;

import com.sparta.clone77.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private Long productId;
    private Map<String, Boolean> productType = new HashMap<>();
    private int price;
    private String serving;
    private String name;
    private String image;
    private List<String> option = new ArrayList<>();

    public ProductResponseDto(Product product){
        this.productId = product.getId();
        this.price = product.getPrice();
        this.serving = product.getServing();
        this.name = product.getName();
        this.image = product.getImage();
        this.option.addAll(Arrays.asList(product.getSelector().split(";")));
        if ( this.name.contains("초신선") ) { productType.put("fresh",true); }
        else { productType.put("fresh",false); }
        if ( this.name.contains("무항생") ) { productType.put("zero",true); }
        else { productType.put("zero",false); }
    }

}
