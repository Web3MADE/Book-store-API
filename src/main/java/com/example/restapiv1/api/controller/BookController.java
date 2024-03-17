package com.example.restapiv1.api.controller;

import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.repository.BookRepository;
import com.example.restapiv1.api.services.BookServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/books")
@Tag(name = "Bookstore", description = "The Bookstore API")
public class BookController {
    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/")
    @Operation(
            summary = "Get all books",
            description = "Get all books"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Books successfully fetched from database"),
            @ApiResponse(code = 204, message = "No books in database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> bookList = bookService.getAllBooks();

            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a book",
            description = "Get a book by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book successfully fetched from database"),
            @ApiResponse(code = 404, message = "Book not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
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
    @PostMapping("/")
    @Operation(
            summary = "Add a new book",
            description = "Add a new book"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book successfully added to database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book bookObj = bookService.saveBook(book);

        return new ResponseEntity<>(bookObj, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a book",
            description = "Update a book by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book successfully updated to database"),
            @ApiResponse(code = 404, message = "Book not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
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
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a book",
            description = "Delete book by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book successfully deleted from database"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Book> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


