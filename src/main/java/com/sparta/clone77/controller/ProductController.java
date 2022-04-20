package com.sparta.clone77.controller;

import com.sparta.clone77.dto.ProductDetailDto;
import com.sparta.clone77.dto.ProductResponseDto;
import com.sparta.clone77.service.ProductService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 전체 조회
    @GetMapping("/products")
    public List<ProductResponseDto> readProducts(
            @RequestParam(defaultValue = "pork")  String tab){
        return productService.getProducts(tab);
    }

    // 상품 일부 조회
    @GetMapping("/products/{productId}")
    public ProductDetailDto productDetail(@PathVariable Long productId){
        return productService.getDetail(productId);
    }

}
