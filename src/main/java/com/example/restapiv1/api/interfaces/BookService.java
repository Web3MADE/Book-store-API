package com.example.restapiv1.api.interfaces;

import com.example.restapiv1.api.model.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Optional<Book> getBookById(Long id);
    Book updateBook(Book oldBookData, Book newBookData);
    Book saveBook(Book book);
    void deleteBookById(Long id);
}
