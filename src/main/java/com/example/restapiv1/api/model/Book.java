package com.example.restapiv1.api.model;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(description = "This table holds Book information")
public class Book {
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

