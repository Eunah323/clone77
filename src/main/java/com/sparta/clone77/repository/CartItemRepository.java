package com.sparta.clone77.repository;

import com.sparta.clone77.model.Cart;
import com.sparta.clone77.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCart(Cart cart);

    @Query("select i from CartItem i where i.productId = :productId and i.cart = :cart")
    CartItem findCartItem(@Param("productId") Long productId, @Param("cart") Cart cart);
}
