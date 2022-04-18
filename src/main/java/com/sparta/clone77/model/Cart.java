package com.sparta.clone77.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(mappedBy = "cart")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;
}
