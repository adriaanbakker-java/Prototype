package com.example.beheerleveringsvoorwaarden.repo;

import com.example.beheerleveringsvoorwaarden.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Book findByIsbn(String isbn);
}
