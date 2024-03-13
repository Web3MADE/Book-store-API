package com.example.restapiv1.api.services;

import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private List<Book> mockBooks;
    private Book bookOne;
    private Book bookTwo;

    @BeforeEach
    void setUp() {
        // Initialize your mock data here - for example, a list of books
        bookOne = new Book(1L, "Book Title One", "Author One", new BigDecimal("15.99"), "Genre One");
        bookTwo = new Book(2L, "Book Title Two", "Author Two", new BigDecimal("9.99"), "Genre Two");
;        mockBooks = Arrays.asList(
                bookOne, bookTwo
        );
    }

    @Test
    void whenGetAllBooks_thenReturnAllBooks() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(mockBooks);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertEquals(mockBooks.size(), result.size(), "The size of the returned book list should match the mock");
        assertEquals(mockBooks, result, "The returned book list should match the mock books");
    }

    @Test
    void givenGetBookById_whenBookExists_thenReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookOne));

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertEquals(mockBooks.getFirst(), foundBook.orElse(null), "The found book should match the expected book");
    }

    @Test
    void givenSaveBook_whenBookIsValid_thenReturnBook() {
        when(bookRepository.save(bookOne)).thenReturn(bookOne);

        Book savedBook = bookService.saveBook(bookOne);

        assertEquals(bookOne, savedBook, "The saved book should be the same");
    }

    @Test
    void givenUpdateBook_whenBookIsUpdated_thenReturnUpdatedBook() {
        Book updatedBook = bookService.updateBook(bookOne, bookTwo);

        assertEquals(bookTwo.getTitle(), updatedBook.getTitle(), "Titles must match");
        assertEquals(bookTwo.getAuthor(), updatedBook.getAuthor(), "Author must match");

    }
}