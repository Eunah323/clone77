package com.sparta.clone77.service;

import com.sparta.clone77.dto.ProductDetailDto;
import com.sparta.clone77.dto.ProductResponseDto;
import com.sparta.clone77.model.Product;
import com.sparta.clone77.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 조회
    public List<ProductResponseDto> getProducts(String tab){

        List<Product> products = productRepository.findAllByCategory(tab);
        List<ProductResponseDto> responseDtos = new ArrayList<>();

        for ( Product product : products ) {
            ProductResponseDto responseDto = new ProductResponseDto(product);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    // 상세조회
    public ProductDetailDto getDetail(Long productId){
        return new ProductDetailDto(productRepository.findById(productId)
                .orElseThrow( ()-> new NullPointerException("상품정보가 존재하지 않습니다.") ));
    }

}
