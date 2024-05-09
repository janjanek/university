package com.university.bibliotheca.service;

import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.ports.BookPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class BookService {

    private BookPort mongoBookAdapter;

    @Autowired
    public BookService(BookPort mongoBookAdapter){
        this.mongoBookAdapter = mongoBookAdapter;
    }

    public void saveBook(Book book){
        mongoBookAdapter.saveBook(book);
    }

    public Book findBook(String id){
       return mongoBookAdapter.findBook(id);
    }


    public List<Book> findBooksByName(String name){//TODO: Implement method
        return mongoBookAdapter.findBooksByName(name);
    }

    public Book findAvailableBookByName(String name){
        return mongoBookAdapter.findAvailableBookByName(name);
    }

    public void changeBorrowStatus(String bookId, boolean isBorrowed, String borrower, Date borrowEnd) {
        Book retrievedBook = findBook(bookId);
        Book changedStatusBook = new Book(retrievedBook.getId(), retrievedBook.getName(),  retrievedBook.getAuthor(), isBorrowed, borrower, Date.from(Instant.now()), borrowEnd);
        saveBook(changedStatusBook);
    }

}
