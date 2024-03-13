package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.interfaces.ShoppingCartService;
import com.example.restapiv1.api.model.CartItem;
import com.example.restapiv1.api.services.ShoppingCartServiceImpl;
import com.example.restapiv1.api.services.ShoppingCartServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

// TODO: implement timeouts and asynchronous calls to prevent spam attacks
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartServiceImpl shoppingCartServiceImpl;

    public ShoppingCartController(ShoppingCartServiceImpl shoppingCartServiceImpl) {
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
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

    @PostMapping("/addBookToCart/{id}/quantity/{quantity}")
    public ResponseEntity<CartItem> addBookToCart(@PathVariable Long id, @PathVariable int quantity) {
        try {
            CartItem addedCartItem = shoppingCartServiceImpl.addBookToCart(id, quantity);

            return new ResponseEntity<>(addedCartItem, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
