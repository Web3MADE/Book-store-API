package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.interfaces.ShoppingCartService;
import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.model.CartItem;
import com.example.restapiv1.api.services.BookServiceImpl;
import com.example.restapiv1.api.services.ShoppingCartServiceImpl;
import com.example.restapiv1.api.services.ShoppingCartServiceImpl;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@Tag(name = "Shopping Cart", description = "The Shopping Cart API")
public class ShoppingCartController {

    private final ShoppingCartServiceImpl shoppingCartServiceImpl;
    private final BookServiceImpl bookServiceImpl;

    public ShoppingCartController(ShoppingCartServiceImpl shoppingCartServiceImpl, BookServiceImpl bookServiceImpl) {
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping("/")
    @Operation(
            summary = "Get all cart items",
            description = "Get all cart items"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cart items successfully fetched from database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
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

    @GetMapping("/total")
    @Operation(
            summary = "Get total price of cart",
            description = "Get total price of cart items"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated total price"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<BigDecimal> getTotalPrice() {
        BigDecimal totalPrice = shoppingCartServiceImpl.getTotalPrice();
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }

    @PostMapping("/{bookId}/quantity/{quantity}")
    @Operation(
            summary = "Add book to cart",
            description = "Add book to cart"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book successfully added to cart"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
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
