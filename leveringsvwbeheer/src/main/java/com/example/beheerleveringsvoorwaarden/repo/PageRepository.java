package com.example.beheerleveringsvoorwaarden.repo;

import com.example.beheerleveringsvoorwaarden.model.Book;
import com.example.beheerleveringsvoorwaarden.model.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PageRepository extends CrudRepository<Page, Long> {
    List<Page> findByBook(Book book, Sort sort);
}
