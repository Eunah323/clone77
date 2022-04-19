package com.sparta.clone77.dto;



import com.sparta.clone77.model.OriginalProduct;
import com.sparta.clone77.model.Selects;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReturnTwoDto {
    private List<OriginalProduct> originalProductList;
    private List<Selects> selectsList;

    public ReturnTwoDto(List<OriginalProduct> originalProductList, List<Selects> selectsList) {
        this.originalProductList = originalProductList;
        this.selectsList = selectsList;
    }

}
