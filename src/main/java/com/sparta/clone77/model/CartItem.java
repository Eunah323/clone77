package com.sparta.clone77.model;

import com.sparta.clone77.dto.CartRequestDto;
import com.sparta.clone77.dto.CartUpdateReqeustDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String options;

    public CartItem(Cart cart, CartRequestDto requestDto) {
        this.cart = cart;
        this.productId = requestDto.getProductId();
        this.options = requestDto.getOption();
        this.quantity = requestDto.getQuantity();
    }

    public void update(CartUpdateReqeustDto reqeustDto){
        if (reqeustDto.isOperator()){ this.quantity++; }
        else { this.quantity--; }
    }

    public void add(int add){
        this.quantity = this.quantity + add;
    }
}
