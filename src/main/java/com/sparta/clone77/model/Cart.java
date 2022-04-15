package com.sparta.clone77.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private int orderCount;

    @OneToMany(mappedBy = "cart")
    private List<OrderItem> orderItems;
}
