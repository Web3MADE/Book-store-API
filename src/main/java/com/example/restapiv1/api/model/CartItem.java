package com.example.restapiv1.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Many cart items can refer to one book
    @ManyToOne
    private Book book;
    private int quantity;

    public CartItem(Book book, int cartItemQuantity) {
        this.book = book;
        this.quantity = cartItemQuantity;
    }
}
