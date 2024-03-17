package com.example.restapiv1.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
// AllArgsConstructor = generates constructor with one argument for each field in class without manually assigning it
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Book {
    // @Id = primary key of Book Entity
    // @GeneratedValue = simplifies the generation of unique primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private String category;

    public Book(String title, String author, BigDecimal price, String category) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
    }
}

