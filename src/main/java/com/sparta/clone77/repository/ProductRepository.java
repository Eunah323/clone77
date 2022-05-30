package com.sparta.clone77.repository;

import com.sparta.clone77.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(String tab);
    Product findByName(String name);
}
