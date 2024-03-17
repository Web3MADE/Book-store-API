package com.example.restapiv1.api.services;

import com.example.restapiv1.api.interfaces.BookService;
import com.example.restapiv1.api.interfaces.ShoppingCartService;
import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.model.CartItem;
import com.example.restapiv1.api.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository cartItemRepository;

    public ShoppingCartServiceImpl(CartItemRepository cartItemRepository ) {
        this.cartItemRepository = cartItemRepository;
    }
    public CartItem addBookToCart(Book book, int quantity) {
        Optional<CartItem> existingCartItem = cartItemRepository.findByBook(book);
        CartItem cartItem;

        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
        }
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<CartItem>(cartItemRepository.findAll());
    }

    public BigDecimal getTotalPrice() {
       List<CartItem> totalCartItems = cartItemRepository.findAll();
       BigDecimal totalPrice = BigDecimal.ZERO;
       if (totalCartItems.isEmpty()) {
           return totalPrice;
       }

       for (CartItem item : totalCartItems) {
           Book book = item.getBook();
           if (book != null) {
               BigDecimal price = book.getPrice();
               if (price != null) {
                   BigDecimal itemTotalPrice = price.multiply(BigDecimal.valueOf(item.getQuantity()));
                   totalPrice = totalPrice.add(itemTotalPrice);
               }
           }

       }

       return totalPrice;

    }

}
