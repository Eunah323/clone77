package com.sparta.clone77.service;


import com.sparta.clone77.crawling.Webdriver;
import com.sparta.clone77.dto.ReturnTwoDto;
import com.sparta.clone77.model.OriginalProduct;
import com.sparta.clone77.model.Selects;
import com.sparta.clone77.repository.OriginalProductRepository;
import com.sparta.clone77.repository.SelectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final OriginalProductRepository originalProductRepository;
    private final SelectsRepository selectsRepository;
    private final Webdriver webdriver;

    public List<OriginalProduct> getProducts() {
        return originalProductRepository.findAll();
    }

    @PostConstruct
    public void crawlingStoreData() {
        String targetUrl = "https://serve.yookgak.com/preload-data";
        ReturnTwoDto returnTwoDto = webdriver.useDriver(targetUrl);
        List<OriginalProduct> originalProductList = returnTwoDto.getOriginalProductList();
        List<Selects> selectsList = returnTwoDto.getSelectsList();
        for(OriginalProduct originalProduct : originalProductList) {
            originalProductRepository.save(originalProduct);
        }
        for(Selects selects : selectsList) {
            selectsRepository.save(selects);
        }
    }

    public List<OriginalProduct> readCategoryProducts(String categoryName) {
        List<OriginalProduct> originalProductList = originalProductRepository.findProductsByCategory(categoryName);
        return originalProductList;
    }
}
