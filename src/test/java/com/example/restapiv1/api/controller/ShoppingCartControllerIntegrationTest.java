package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.model.CartItem;
import com.example.restapiv1.api.repository.BookRepository;
import com.example.restapiv1.api.repository.CartItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

    private static final int CART_ITEM_QUANTITY = 2;

    Book book;
    CartItem cartItem;

    @BeforeEach
    public void setup() {
        book = new Book("Test Book", "Test Author", new BigDecimal("19.99"), "Test Category");
        book = bookRepository.save(book);

        cartItem = new CartItem(book, CART_ITEM_QUANTITY);
        cartItemRepository.save(cartItem);
    }

    @AfterEach
    public void cleanup() {
        cartItemRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void givenAddBookToCart_whenBookAdded_returnCartItem() throws Exception {
        int quantity = 5;
        mockMvc.perform((post("/cart/addBookToCart/{id}/quantity/{quantity}", book.getId(), quantity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartItem.getId()))
                .andExpect(jsonPath("$.book.id").value(cartItem.getBook().getId()))
                .andExpect(jsonPath("$.quantity", is(quantity + CART_ITEM_QUANTITY)));

    }
    @Test
    public void getCartItems_whenCartHasBook_ReturnsOkWithContent() throws Exception {

        mockMvc.perform(get("/cart/getCartItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(cartItem.getId().intValue())))
                .andExpect(jsonPath("$[0].book.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$[0].book.title", is(book.getTitle())))
                .andExpect(jsonPath("$[0].quantity", is(cartItem.getQuantity())))
                .andDo(print());
    }

    @Test
    public void givenGetTotalPrice_whenCartHasItems_thenReturnTotalPrice() throws Exception {
        BigDecimal expectedTotalPrice = new BigDecimal("39.98");
        mockMvc.perform(get("/cart/getTotalPrice"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedTotalPrice.toString())); // Check the total price value
    }


}