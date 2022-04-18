package com.sparta.clone77.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter // Getter 추가
@Table(name = "options")
public class Option {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

}
