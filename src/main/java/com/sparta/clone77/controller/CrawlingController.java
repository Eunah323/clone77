package com.sparta.clone77.controller;




import com.sparta.clone77.model.OriginalProduct;
import com.sparta.clone77.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingService crawlingService;

    @GetMapping("/api/products")
    public List<OriginalProduct> getProducts() {
        return crawlingService.getProducts();
    }

    @GetMapping("/api/products/{categoryName}")
    public List<OriginalProduct> getCategoryProducts(@PathVariable String categoryName) {
        return crawlingService.readCategoryProducts(categoryName);
    }
}
