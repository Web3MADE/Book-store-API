package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.repository.BookRepository;
import com.example.restapiv1.api.services.BookServiceImpl;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest // Setup full application context
@AutoConfigureMockMvc // Setup MockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll();
        Book bookOne = new Book(1L, "Book1", "Author1", new BigDecimal("9.99"), "Category1");
        Book bookTwo = new Book(2L, "Book2", "Author1", new BigDecimal("19.99"), "Category1");
        bookRepository.save(bookOne);
        bookRepository.save(bookTwo);
    }

    @Test
    public void givenGetAllBooks_whenAllBooksFound_thenReturnListOfBooks() throws Exception {

        mockMvc.perform(get("/books/getAllBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].title", is("Book1")))
                .andExpect((ResultMatcher) jsonPath("$[1].title", is("Book2")));
    }

    @Test
    public void givenGetBookById_whenBookFound_thenReturnBook() throws Exception {

        mockMvc.perform(get("/books/getBookById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Book1"))
                .andExpect(jsonPath("$.author").value("Author1"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.category").value("Category1"));
    }

    @Test
    public void givenAddBook_whenBookAdded_thenReturnAddedBook() throws Exception {
        // Given
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("title", "Book1");
        jsonRequest.put("author", "Author1");
        jsonRequest.put("price", 9.99);
        jsonRequest.put("category", "Category1");
        // When & Then
        mockMvc.perform(post("/books/addBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book1"))
                .andExpect(jsonPath("$.author").value("Author1"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.category").value("Category1"));
    }
}
