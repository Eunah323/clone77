package com.sparta.clone77.service;

import com.sparta.clone77.dto.CartRequestDto;
import com.sparta.clone77.dto.CartResponseDto;
import com.sparta.clone77.dto.StatusDto;
import com.sparta.clone77.model.CartItem;
import com.sparta.clone77.repository.CartItemRepository;
import com.sparta.clone77.repository.CartRepository;
import com.sparta.clone77.repository.ProductRepository;
import com.sparta.clone77.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    public StatusDto addCart(CartRequestDto requestDto, UserDetailsImpl userDetails){
        // 유저 카트 내에 카트 아이템 추가 및 반환
        cartItemRepository.save(new CartItem(productRepository
                .findById(requestDto.getProductId())
                .orElseThrow( () -> new NullPointerException("상품정보가 존재하지 않습니다.")),
                cartRepository.findByUser(userDetails.getUser())));
        return new StatusDto("장바구니에 상품 추가 완료");
    }

    public CartResponseDto getCart(UserDetailsImpl userDetails){
        return new CartResponseDto(cartRepository.findByUser(userDetails.getUser()));
    }

    public StatusDto delCart(UserDetailsImpl userDetails){
        cartItemRepository.deleteAllByCart(
                cartRepository.findByUser(userDetails.getUser()));
        return new StatusDto("주문 및 장바구니 삭제 완료");
    }

}
