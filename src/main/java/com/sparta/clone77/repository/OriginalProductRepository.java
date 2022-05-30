package com.sparta.clone77.repository;



import com.sparta.clone77.model.OriginalProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OriginalProductRepository extends JpaRepository<OriginalProduct, Long> {
    List<OriginalProduct> findProductsByCategory(String category);
}
