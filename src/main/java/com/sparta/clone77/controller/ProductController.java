package com.sparta.clone77.controller;

import com.sparta.clone77.dto.ProductResponseDto;
import com.sparta.clone77.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 목록 조회
    @GetMapping("/products")
    public List<ProductResponseDto> readProducts(
            @RequestParam(defaultValue = "pork")  String tab){
        return productService.getProducts(tab);
    }

}
