package com.example.restapiv1.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(description = "This table holds cart item information")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "This is an auto-generated ID for a cart item")
    private Long id;
    @ManyToOne
    @ApiModelProperty(notes = "This is the book contained by the cart")
    private Book book;
    @ApiModelProperty(notes = "This is the quantity of the given cart item")
    private int quantity;

    public CartItem(Book book, int cartItemQuantity) {
        this.book = book;
        this.quantity = cartItemQuantity;
    }
}
