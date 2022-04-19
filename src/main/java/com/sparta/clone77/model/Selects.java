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
    private OriginalProduct originalProduct;

    public Selects(String displayid, String name, OriginalProduct originalProduct) {
        this.displayid = displayid;
        this.name = name;
        this.originalProduct = originalProduct;
    }
}
