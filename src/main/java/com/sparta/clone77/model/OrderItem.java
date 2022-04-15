package com.sparta.clone77.model;

import javax.persistence.*;

@Entity
public class OrderItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn
    private Order order;

    @ManyToOne
    private Cart cart;



}
