package com.sparta.clone77.service;

import com.sparta.clone77.dto.*;
import com.sparta.clone77.model.Cart;
import com.sparta.clone77.model.CartItem;
import com.sparta.clone77.model.Product;
import com.sparta.clone77.repository.CartRepository;
import com.sparta.clone77.repository.CartItemRepository;
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
    @Transactional
    public StatusDto addCart(CartRequestDto requestDto, UserDetailsImpl userDetails){

        // 장바구니 확인과 생성
        Optional<Cart> optionalCart = cartRepository.findByUserId(userDetails.getUser().getId());
        Cart cart = optionalCart.orElseGet(() -> cartRepository.save(new Cart(userDetails.getUser())));

        // 아이템의 product id, cart, option이 동일한 경우에는 수량을 추가해 줘야함
        CartItem cartItems = cartItemRepository.findCartItem(requestDto.getProductId(), cart, requestDto.getOption());

        try
        {
            cartItems.getCart();
            cartItems.add(requestDto.getQuantity());
        }
        catch (Exception e)
        {
            //장바구니 내에 아이템 추가
            cartItemRepository.save(new CartItem(cart, requestDto));
        }
        return new StatusDto("장바구니에 상품 추가 완료");
    }

    // 장바구니 조회
    public CartResponseDto getCart(UserDetailsImpl userDetails){

        Cart cart = cartRepository.findByUserId(userDetails.getUser().getId())
                .orElseThrow(() -> new NullPointerException("오류"));
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

        Cart cart = cartRepository.findByUserId(userDetails.getUser().getId())
                .orElseThrow(()-> new NullPointerException("사용자 없음"));

        List<CartItem> cartItems = cartItemRepository.findCartItemsByCart(cart);

        for (CartItem item : cartItems){
            cartItemRepository.deleteById(item.getId());
        }

        return new StatusDto("주문 및 장바구니 삭제 완료");
    }

    // 장바구니 아이템 업데이트(수량 변경)
    @Transactional
    public StatusDto putCart(CartUpdateReqeustDto reqeustDto, UserDetailsImpl userDetails){

        CartItem cartItem = cartRepository
                .findByUserId(userDetails.getUser().getId())
                .map(cart -> cartItemRepository.findCartItem(reqeustDto.getProductId(), cart, reqeustDto.getOptions()))
                .orElseThrow(() -> new NullPointerException("해당 상품이 존재하지 않습니다."));

        if (cartItem.getQuantity() > 0) {
            cartItem.update(reqeustDto);
            return new StatusDto("수량변경");}
        else {
            return new StatusDto("0회 이상만 변경가능"); }
    }

    // 장바구니 아이템 개별 삭제
    public StatusDto delcart(Long productId, OptionDto option, UserDetailsImpl userDetails){
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCart(cartRepository.findByUserId(
                userDetails.getUser().getId()).orElseThrow(() -> new NullPointerException("회원정보가 없습니다.")));
        for( CartItem item : cartItems ){
            if ( item.getOptions().equals(option) ){
                cartItemRepository.delete(item);
            }
        }
//        CartItem cartItem = cartRepository
//                .findByUserId(userDetails.getUser().getId())
//                .map( cart -> cartItemRepository.findCartItem(productId, cart, option.getOption()))
//                .orElseThrow(() -> new NullPointerException("해당 상품이 존재하지 않습니다."));
//
//        cartItemRepository.delete(cartItem);
//
        return new StatusDto("아이템 삭제 완료");
    }

}
