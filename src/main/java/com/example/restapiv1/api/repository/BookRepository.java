package com.example.restapiv1.api.repository;

import com.example.restapiv1.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JPA simplifies database access, specifically the persistence layer
// Accepts Book, the entity class managed by the repository, Long is the type of primary key for the Book entity
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
