package com.sparta.clone77.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Selects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String displayid;

    private String name;

    @JsonBackReference
    @ManyToOne
    private Product product;

    public Selects(String displayid, String name, Product product) {
        this.displayid = displayid;
        this.name = name;
        this.product = product;
    }
}
