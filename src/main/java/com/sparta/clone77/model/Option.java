package com.sparta.clone77.model;

import javax.persistence.*;

@Entity
@Table(name = "options")
public class Option {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn
    private Product product;
}
