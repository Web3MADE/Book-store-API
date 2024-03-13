package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.model.CartItem;
import com.example.restapiv1.api.services.ShoppingCartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ShoppingCartControllerTest {

    @Mock
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenCartHasItems_thenStatusOk() {
        // Arrange
        List<CartItem> mockCartItems = new ArrayList<>();
        Book book = new Book(1L, "testTitle", "author", new BigDecimal(100), "category");
        mockCartItems.add(new CartItem(1L, book, 5));
        when(shoppingCartServiceImpl.getCartItems()).thenReturn(mockCartItems);

        // Act
        ResponseEntity<List<CartItem>> response = shoppingCartController.getCartItems();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void givenGetTotalPrice_whenPriceExists_thenStatusOk() {
        BigDecimal totalPrice = new BigDecimal(100);
        when(shoppingCartServiceImpl.getTotalPrice()).thenReturn(totalPrice);
        ResponseEntity<BigDecimal> response = shoppingCartController.getTotalPrice();

        assertEquals(response.getBody(), new BigDecimal(100));
    }
}