package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.repository.BookRepository;
import com.example.restapiv1.api.services.BookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// TODO: refactor controller logic into bookService later
@RestController
@RequestMapping("/books")
public class BookController {
    // BookController depends on BookRepository, Spring's DI container will inject an instance of BookRepository into BookController
    // when it creates the controller bean
    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            // call bookService to get all books
            List<Book> bookList = bookService.getAllBooks();

            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            // @DEV can we instantiate ResonseEntity once and just set statuses cleaner?
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            Optional<Book> bookData = bookService.getBookById(id);

            if (bookData.isPresent()) {
                return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book bookObj = bookService.saveBook(book);

        return new ResponseEntity<>(bookObj, HttpStatus.OK);
    }
    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookData) {
        Optional<Book> oldBookData = bookService.getBookById(id);

        if (oldBookData.isPresent()) {
            Book bookToUpdate = oldBookData.get();
            Book updatedBookData = bookService.updateBook(bookToUpdate, newBookData);
            Book savedBookData = bookService.saveBook(updatedBookData);
            return new ResponseEntity<>(savedBookData, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


