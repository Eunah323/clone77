package com.sparta.clone77.repository;

import com.sparta.clone77.model.Cart;
import com.sparta.clone77.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
