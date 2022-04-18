package com.sparta.clone77.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String image;

    @Column
    private String content;

    @Column
    private int price; // price는 int 자료형

    // 정보제공을 위해서는 카테코리와 서빙에 대한 자료가 필요하여 추가하였습니다.
    @Column
    private String category;

    @Column
    private String serving;

    @OneToMany(mappedBy = "product")
    private List<Option> options;

}
