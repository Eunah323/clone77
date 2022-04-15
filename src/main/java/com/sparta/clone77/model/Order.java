package com.sparta.clone77.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id;

    @Column
    private int totalPrice;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

}
