package com.sparta.clone77.model;

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

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private int quantity;

    // 옵션 입력 필요
    @Column
    private String option;

    public CartItem(Product product, Cart cart) {
        this.cart = cart;
        this.product = product;
        this.quantity++;
    }

}
