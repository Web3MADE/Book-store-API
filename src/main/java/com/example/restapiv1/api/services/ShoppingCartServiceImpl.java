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
    private final BookService bookService;


    public ShoppingCartServiceImpl(CartItemRepository cartItemRepository, BookService bookService) {
        this.cartItemRepository = cartItemRepository;
        this.bookService = bookService;
    }

    public CartItem addBookToCart(Long bookId, int quantity) {
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        if (bookOptional.isEmpty()) {
            throw new RuntimeException("Book not found");
        }
        Book book = bookOptional.get();

        Optional<CartItem> existingCartItem = cartItemRepository.findById(bookId);
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
