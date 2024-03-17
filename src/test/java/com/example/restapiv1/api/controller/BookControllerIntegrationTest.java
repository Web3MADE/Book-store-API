package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.repository.BookRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;
    Book   bookOne;
    Book bookTwo;
    @BeforeEach
    public void setup() {
        bookOne = bookRepository.save(new Book( "Book1", "Author1", new BigDecimal("9.99"), "Category1"));
        bookTwo = bookRepository.save(new Book( "Book2", "Author1", new BigDecimal("19.99"), "Category1"));
    }

    @AfterEach
    public void cleanup() {
        bookRepository.deleteAll();
    }
    @Test
    public void givenGetAllBooks_whenAllBooksFound_thenReturnListOfBooks() throws Exception {

        mockMvc.perform(get("/books/getAllBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].title", is(bookOne.getTitle())))
                .andExpect((ResultMatcher) jsonPath("$[1].title", is(bookTwo.getTitle())));
    }

    @Test
    public void givenGetBookById_whenBookFound_thenReturnBook() throws Exception {

        mockMvc.perform(get("/books/getBookById/" + bookOne.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookOne.getId()))
                .andExpect(jsonPath("$.title").value(bookOne.getTitle()))
                .andExpect(jsonPath("$.author").value(bookOne.getAuthor()))
                .andExpect(jsonPath("$.price").value(bookOne.getPrice()))
                .andExpect(jsonPath("$.category").value(bookOne.getCategory()));
    }

    @Test
    public void givenAddBook_whenBookAdded_thenReturnAddedBook() throws Exception {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("title", "Book3");
        jsonRequest.put("author", "Author3");
        jsonRequest.put("price", 9.99);
        jsonRequest.put("category", "Category1");
        mockMvc.perform(post("/books/addBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book3"))
                .andExpect(jsonPath("$.author").value("Author3"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.category").value("Category1"));
    }
}
