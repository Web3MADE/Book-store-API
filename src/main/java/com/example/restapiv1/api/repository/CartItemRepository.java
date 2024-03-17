package com.example.restapiv1.api.repository;

import com.example.restapiv1.api.model.Book;
import com.example.restapiv1.api.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByBook(Book book);
}
