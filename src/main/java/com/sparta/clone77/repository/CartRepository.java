package com.sparta.clone77.repository;

import com.sparta.clone77.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);

    @Query("select distinct c, i from Cart c, CartItem i left join fetch c.cartItems where c.userId = :userId and i.cart = c")
    Cart findCartFetchJoin(@Param("userID") Long userId);
}
