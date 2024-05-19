package com.university.bibliotheca.domain.service;

import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.application.ports.BookPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class BookService {

    private BookPort mongoBookAdapter;

    @Autowired
    public BookService(BookPort mongoBookAdapter) {
        this.mongoBookAdapter = mongoBookAdapter;
    }

    public void saveBook(Book book) {
        mongoBookAdapter.saveBook(book);
    }

    public Book findBook(String id) {
        return mongoBookAdapter.findBook(id);
    }

    public boolean deleteBook(String id) {
        if (!findBook(id).isBorrowed()) {
            mongoBookAdapter.deleteBook(id);
            return true;
        } else{
            return false;
        }
    }

    public List<Book> findAllBooks() {
        return mongoBookAdapter.findAllBooks();
    }

    public List<Book> findBooksByName(String name) {
        return mongoBookAdapter.findBooksByName(name);
    }

    public Optional<Book> findAvailableBookByName(String name) {
        return mongoBookAdapter.findAvailableBookByName(name);
    }

    public Optional<Book> findBorrowedBookForUser(String userId, String bookName){
        return mongoBookAdapter.findBorrowedBookForUser(userId, bookName);
    }

    public void changeBorrowStatus(String bookId, boolean isBorrowed, String borrower, Date borrowStart, Date borrowEnd) {
        Book retrievedBook = findBook(bookId);
        Book changedStatusBook = new Book(retrievedBook.getId(), retrievedBook.getName(), retrievedBook.getAuthor(), isBorrowed, borrower, borrowStart, borrowEnd);
        saveBook(changedStatusBook);
    }

}
