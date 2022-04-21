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

//    public List<ProductResponseDto> allProducts(){
//        List<Product> products = productRepository.findAll();
//        List<ProductResponseDto> responseDtos = new ArrayList<>();
//
//        for ( Product product : products ) {
//            ProductResponseDto responseDto = new ProductResponseDto(product);
//            responseDtos.add(responseDto);
//        }
//
//        return responseDtos;
//    }

    // 상세조회
    public ProductDetailDto getDetail(Long productId){
        return new ProductDetailDto(productRepository.findById(productId)
                .orElseThrow( ()-> new NullPointerException("상품정보가 존재하지 않습니다.") ));
    }
    public List<ProductResponseDto> allProducts(){

        List<Product> products = new ArrayList<>();
        Product p1 = productRepository.findByName("초신선 돼지 삼겹살 구이용");
        Product p2 = productRepository.findByName("초신선 닭볶음탕");
        Product p3 = productRepository.findByName("초신선 등심 돈까스");
        Product p4 = productRepository.findByName("초신선 동물복지 무항생제 유정란");
        Product p5 = productRepository.findByName("초신선 무항생제 우유");
        Product p6 = productRepository.findByName("초신선 무항생제 이유식용 한우 우둔 다짐육");
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);
        products.add(p6);
        List<ProductResponseDto> responseDtos = new ArrayList<>();

        for ( Product product : products ) {
            ProductResponseDto responseDto = new ProductResponseDto(product);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }


}
