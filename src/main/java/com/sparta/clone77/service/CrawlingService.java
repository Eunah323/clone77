package com.sparta.clone77.service;


import com.sparta.clone77.crawling.Webdriver;
import com.sparta.clone77.dto.ReturnTwoDto;
import com.sparta.clone77.model.OriginalProduct;
import com.sparta.clone77.model.Product;
import com.sparta.clone77.model.ProductClone;
import com.sparta.clone77.model.Selects;
import com.sparta.clone77.repository.OriginalProductRepository;
import com.sparta.clone77.repository.ProductRepository;
import com.sparta.clone77.repository.SelectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final OriginalProductRepository originalProductRepository;
    private final SelectsRepository selectsRepository;
    private final Webdriver webdriver;
    private final ProductRepository productRepository;

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
        List<ProductClone> clones = new ArrayList<>();

        for(OriginalProduct originalProduct : originalProductList) {
            ProductClone clone = new ProductClone();

            clone.setCategory(originalProduct.getCategory());

            clone.setName(originalProduct.getDetail_name());

            String price = originalProduct.getList_price().split("원")[0];
            clone.setPrice(Integer.parseInt(price.replaceAll(",","")));

            clone.setImage(originalProduct.getList_thumbnail_web());

            String serving = originalProduct.getList_price().split("/")[1];
            clone.setServing(serving);

            clones.add(clone);
        }

        for(int i = 0 ; i < selectsList.size() ; i++) {
            Long num = selectsList.get(i).getOriginalproduct().getId();
            clones.get(num.intValue()-1)
                    .setSelector(
                            clones.get(num.intValue()-1).getSelector() + selectsList.get(i).getName() + ";");
        }

        for (ProductClone c : clones){
            c.setSelector(c.getSelector().substring(0, c.getSelector().length()-1));
            c.setSelector(c.getSelector().replace("null",""));
            Product product = new Product(c);
            productRepository.save(product);
        }
    }

    public List<OriginalProduct> readCategoryProducts(String categoryName) {
        List<OriginalProduct> originalProductList = originalProductRepository.findProductsByCategory(categoryName);
        return originalProductList;
    }


}
