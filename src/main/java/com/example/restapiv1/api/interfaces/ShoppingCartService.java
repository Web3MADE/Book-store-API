package com.example.restapiv1.api.interfaces;

import com.example.restapiv1.api.model.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartService {
    CartItem addBookToCart(Long bookId, int quantity);
    List<CartItem> getCartItems();
    BigDecimal getTotalPrice();
}
