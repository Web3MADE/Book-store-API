package com.example.restapiv1.api.repository;

import com.example.restapiv1.api.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
