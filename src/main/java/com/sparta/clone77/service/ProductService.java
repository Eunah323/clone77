package com.sparta.clone77.service;

import com.sparta.clone77.dto.ProductResponseDto;
import com.sparta.clone77.model.Product;
import com.sparta.clone77.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponseDto> getProducts(String tab){

        List<Product> products = productRepository.findAllByCategory(tab);
        List<ProductResponseDto> responseDtos = new ArrayList<>();

        for ( Product product : products ) {
            ProductResponseDto responseDto = new ProductResponseDto(product);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

}
