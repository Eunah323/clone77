package com.sparta.clone77.controller;

import com.sparta.clone77.dto.ProductDetailDto;
import com.sparta.clone77.dto.ProductResponseDto;
import com.sparta.clone77.repository.ProductRepository;
import com.sparta.clone77.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    // 상품 전체 조회
    @GetMapping("/products")
    public List<ProductResponseDto> allProducts(){
        return productService.allProducts();
    }

    // 상품 카테고리 조회
//    @GetMapping("/products")
//    public List<ProductResponseDto> readProducts(
//            @RequestParam(defaultValue = "pork")  String tab){
//        return productService.getProducts(tab);
//    }

    // 상품 일부 조회
    @GetMapping("/products/{productId}")
    public ProductDetailDto productDetail(@PathVariable Long productId){
        return productService.getDetail(productId);
    }

    // 상세조회
    public ProductDetailDto getDetail(Long productId){
        return new ProductDetailDto(productRepository.findById(productId)
                .orElseThrow( ()-> new NullPointerException("상품정보가 존재하지 않습니다.") ));
    }

}
