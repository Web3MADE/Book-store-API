package com.example.restapiv1.api.services;

import com.example.restapiv1.api.interfaces.BookService;
import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookRepository.findAll());
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    // TODO: don't even need this logic for the task
    public Book updateBook(Book oldBookData, Book newBookData) {
        oldBookData.setTitle(newBookData.getTitle());
        oldBookData.setAuthor((newBookData.getAuthor()));
        return oldBookData;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }


}
