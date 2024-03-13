package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.model.CartItem;
import com.example.restapiv1.api.repository.BookRepository;
import com.example.restapiv1.api.repository.CartItemRepository;
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
    private CartItemRepository cartItemRepository; // Assuming you have this

    @Autowired
    private BookRepository bookRepository; // Assuming you have this

    private static final long BOOK_ONE_ID = 1L;
    private static final long CART_ITEM_ID = 1L;
    private static final int CART_ITEM_QUANTITY = 2;



    @BeforeEach
    public void setup() {
        cartItemRepository.deleteAll();
        bookRepository.deleteAll();

        // Create and save a Book
        Book book = new Book(BOOK_ONE_ID, "Test Book", "Test Author", new BigDecimal("19.99"), "Test Category");
        book = bookRepository.save(book);

        // Create and save a CartItem
        CartItem cartItem = new CartItem(CART_ITEM_ID, book, CART_ITEM_QUANTITY); // Assuming constructor CartItem(Book book, int quantity)
        cartItemRepository.save(cartItem);
    }
    // TODO: finish addBookToCart and getTotalPrice tests
    //  then clean up entire codebase and document API in Swagger
    //   Finally, create solid documentation and justifications for selected approach and alternatives
    @Test
    public void givenAddBookToCart_whenBookAdded_returnCartItem() throws Exception {
        int quantity = 5;

        mockMvc.perform((post("/cart/addBookToCart/{id}/quantity/{quantity}", CART_ITEM_ID, quantity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.id").value(CART_ITEM_ID))
                .andExpect(jsonPath("$.quantity", is(quantity + CART_ITEM_QUANTITY)));

    }

    @Test
    public void getCartItems_whenCartIsEmpty_ReturnsNoContent() throws Exception {
        mockMvc.perform(get("/cart/getCartItems"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getCartItems_whenCartIsNotEmpty_ReturnsOkWithContent() throws Exception {
        mockMvc.perform(get("/cart/getCartItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'id':1,'book':{'id':1,'title':'Test Book','author':'Test Author','price':19.99,'category':'Test Category'},'quantity':2}]"));
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