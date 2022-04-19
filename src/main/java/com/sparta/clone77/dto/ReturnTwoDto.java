package com.sparta.clone77.dto;



import com.sparta.clone77.model.Product;
import com.sparta.clone77.model.Selects;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReturnTwoDto {
    private List<Product> productList;
    private List<Selects> selectsList;

    public ReturnTwoDto(List<Product> productList, List<Selects> selectsList) {
        this.productList = productList;
        this.selectsList = selectsList;
    }

}
