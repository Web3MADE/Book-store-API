package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.interfaces.ShoppingCartService;
import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.model.CartItem;
import com.example.restapiv1.api.services.BookServiceImpl;
import com.example.restapiv1.api.services.ShoppingCartServiceImpl;
import com.example.restapiv1.api.services.ShoppingCartServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// TODO: implement timeouts and asynchronous calls to prevent spam attacks
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartServiceImpl shoppingCartServiceImpl;
    // TODO: finish bookserviceimpl name update
    private final BookServiceImpl bookServiceImpl;

    public ShoppingCartController(ShoppingCartServiceImpl shoppingCartServiceImpl, BookServiceImpl bookServiceImpl) {
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping("/getCartItems")
    public ResponseEntity<List<CartItem>> getCartItems() {
        try {
            List<CartItem> cartItems = shoppingCartServiceImpl.getCartItems();

            if (cartItems.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTotalPrice")
    public ResponseEntity<BigDecimal> getTotalPrice() {
        BigDecimal totalPrice = shoppingCartServiceImpl.getTotalPrice();
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }

    @PostMapping("/addBookToCart/{bookId}/quantity/{quantity}")
    public ResponseEntity<CartItem> addBookToCart(@PathVariable Long bookId, @PathVariable int quantity) {
        try {
            Optional<Book> bookOptional = bookServiceImpl.getBookById(bookId);
            if (bookOptional.isEmpty()) {
                throw new RuntimeException("Book not found");
            }

            Book book = bookOptional.get();
            CartItem addedCartItem = shoppingCartServiceImpl.addBookToCart(book, quantity);

            return new ResponseEntity<>(addedCartItem, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
