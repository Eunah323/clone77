package com.sparta.clone77.service;


import com.sparta.clone77.crawling.Webdriver;
import com.sparta.clone77.dto.ReturnTwoDto;
import com.sparta.clone77.model.Product;
import com.sparta.clone77.model.Selects;
import com.sparta.clone77.repository.ProductRepository;
import com.sparta.clone77.repository.SelectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final ProductRepository productRepository;
    private final SelectsRepository selectsRepository;
    private final Webdriver webdriver;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostConstruct
    public void crawlingStoreData() {
        String targetUrl = "https://serve.yookgak.com/preload-data";
        ReturnTwoDto returnTwoDto = webdriver.useDriver(targetUrl);
        List<Product> productList = returnTwoDto.getProductList();
        List<Selects> selectsList = returnTwoDto.getSelectsList();
        for(Product product : productList) {
            productRepository.save(product);
        }
        for(Selects selects : selectsList) {
            selectsRepository.save(selects);
        }
    }

    public List<Product> readCategoryProducts(String categoryName) {
        List<Product> productList = productRepository.findProductsByCategory(categoryName);
        return productList;
    }
}
