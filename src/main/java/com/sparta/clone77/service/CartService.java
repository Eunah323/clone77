package com.sparta.clone77.service;

import com.sparta.clone77.dto.CartRequestDto;
import com.sparta.clone77.dto.CartResponseDto;
import com.sparta.clone77.dto.CartUpdateReqeustDto;
import com.sparta.clone77.dto.StatusDto;
import com.sparta.clone77.model.Cart;
import com.sparta.clone77.model.CartItem;
import com.sparta.clone77.model.Product;
import com.sparta.clone77.repository.CartItemRepository;
import com.sparta.clone77.repository.CartRepository;
import com.sparta.clone77.repository.ProductRepository;
import com.sparta.clone77.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    // 장바구니 추가
    public StatusDto addCart(CartRequestDto requestDto, UserDetailsImpl userDetails){

        // 장바구니 확인과 생성
        Optional<Cart> optionalCart = cartRepository.findByUserId(userDetails.getUser().getId());
        Cart cart;
        cart = optionalCart.orElseGet(() -> cartRepository.save(new Cart(userDetails.getUser())));

        //장바구니 내에 아이템 추가 및 반환
        cartItemRepository.save(new CartItem(cart, requestDto));
        return new StatusDto("장바구니에 상품 추가 완료");
    }

    // 장바구니 조회
    public CartResponseDto getCart(UserDetailsImpl userDetails){

        Cart cart = cartRepository.findCartFetchJoin(userDetails.getUser().getId());
        List<Product> products = new ArrayList<>();
        for ( CartItem item : cart.getCartItems() ){
            products.add(productRepository
                    .findById(item.getProductId())
                            .orElseThrow( () -> new NullPointerException("상품정보가 없습니다.")));
        }
        return new CartResponseDto(cart, products, userDetails.getUser().getOrderCount());
    }

    // 주문완료(장바구니 전체 비우기)
    public StatusDto orderCart(UserDetailsImpl userDetails){

        cartRepository
                .findByUserId(userDetails.getUser().getId())
                .ifPresent(cartItemRepository::deleteAllByCart);

        return new StatusDto("주문 및 장바구니 삭제 완료");
    }

    // 장바구니 아이템 업데이트(수량 변경)
    @Transactional
    public StatusDto putCart(CartUpdateReqeustDto reqeustDto, UserDetailsImpl userDetails){

        CartItem cartItem = cartRepository
                .findByUserId(userDetails.getUser().getId())
                .map(cart -> cartItemRepository.findCartItem(reqeustDto.getProductId(), cart))
                .orElseThrow(() -> new NullPointerException("해당 상품이 존재하지 않습니다."));

        if (cartItem.getQuantity() > 0) {
            cartItem.update(reqeustDto);
            return new StatusDto("수량변경");}
        else {
            return new StatusDto("0회 이상만 변경가능"); }
    }

    // 장바구니 아이템 개별 삭제
    public StatusDto delcart(Long productId, UserDetailsImpl userDetails){

        CartItem cartItem = cartRepository
                .findByUserId(userDetails.getUser().getId())
                .map( cart -> cartItemRepository.findCartItem(productId, cart))
                .orElseThrow(() -> new NullPointerException("해당 상품이 존재하지 않습니다."));

        cartItemRepository.delete(cartItem);

        return new StatusDto("아이템 삭제 완료");
    }

}
