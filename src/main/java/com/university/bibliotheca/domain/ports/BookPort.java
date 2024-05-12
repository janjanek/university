package com.university.bibliotheca.domain.ports;

import com.university.bibliotheca.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookPort {

    void saveBook(Book book);

    Book findBook(String id);

    List<Book> findAllBooks();

    List<Book> findBooksByName(String name);

    Optional<Book> findAvailableBookByName(String name);
}
