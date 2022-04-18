package com.sparta.clone77.repository;

import com.sparta.clone77.model.Cart;
import com.sparta.clone77.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<Long> deleteAllByCart(Cart cart);
}
