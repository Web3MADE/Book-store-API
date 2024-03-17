package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.services.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookServiceImpl bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void givenGetAllBooks_whenBooksExist_thenStatusOk() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "testTitle", "author", new BigDecimal(100), "category"));
        books.add(new Book(2L, "testTitle", "author", new BigDecimal(200), "category"));
        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void givenGetAllBooks_whenNoBooksExist_thenStatusNoContent() {
        List<Book> books = new ArrayList<>();
        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void givenGetAllBooks_whenServiceThrowsException_thenStatusInternalServerError() {
        when(bookService.getAllBooks()).thenThrow(new RuntimeException());

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(ResponseEntity.internalServerError().build().getStatusCode(), response.getStatusCode());
    }

}
