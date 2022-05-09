package com.codegym.repository;

import com.codegym.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends CrudRepository<Book, Long> {
    Iterable<Book> findBookByName (String name);
}
