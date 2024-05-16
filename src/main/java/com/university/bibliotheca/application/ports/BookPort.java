package com.university.bibliotheca.application.ports;

import com.university.bibliotheca.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookPort {

    void saveBook(Book book);

    Book findBook(String id);

    void deleteBook(String id);

    List<Book> findAllBooks();

    List<Book> findBooksByName(String name);

    Optional<Book> findAvailableBookByName(String name);

    Optional<Book> findBorrowedBookForUser(String name, String bookName);
}
