package com.sparta.clone77.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품 이름
    private String detail_name;

    // 상품이미지
    private String list_thumbnail_web;

    // 무항생제 여부
    private String list_tag;

    // 상품 옵션
    private String list_option;

    // 상품 가격 정보 ex) 16,800원/600g
    private String list_price;

    // 상품 카테고리
    private String category;

    // displayid
    private String displayid;

    // selects(드롭다운항목)
    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    private List<Selects> selectsList;

    public Product(String detail_name, String list_thumbnail_web, String list_tag, String list_option, String list_price, String category, String displayid) {
        this.detail_name = detail_name;
        this.list_thumbnail_web = list_thumbnail_web;
        this.list_tag = list_tag;
        this.list_option = list_option;
        this.list_price = list_price;
        this.category = category;
        this.displayid = displayid;
    }

    public void addSelect(List<Selects> selectsList) {
        this.selectsList = selectsList;
    }
}
