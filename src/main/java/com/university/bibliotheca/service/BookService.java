package com.university.bibliotheca.service;

import com.university.bibliotheca.adapter.BookDto;
import com.university.bibliotheca.adapter.mongo.MongoBookAdapter;
import com.university.bibliotheca.domain.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class BookService {

    private MongoBookAdapter mongoBookAdapter;

    @Autowired
    public BookService(MongoBookAdapter mongoBookAdapter){
        this.mongoBookAdapter = mongoBookAdapter;
    }

    public void saveBook(Book book){
        mongoBookAdapter.saveBook(book);
    }

    public Book findBook(String id){
       return mongoBookAdapter.findBook(id);
    }

    public boolean isBookAvailable(String name){//TODO: Implement is book avaible by name
      return mongoBookAdapter.isBookAvailable(name);
    }

    public List<Book> findBooksByName(String name){//TODO: Implement method
        return Collections.emptyList();
    }

    public BookDto findBookDto(String id){
        return mongoBookAdapter.findBook(id).toDto();
    }

    public void changeBorrowStatus(String bookId, boolean isBorrowed, Date borrowEnd) {
        Book retrievedBook = findBook(bookId);
        Book changedStatusBook = new Book(retrievedBook.getId(), retrievedBook.getName(),  retrievedBook.getAuthor(), retrievedBook.getReader(), isBorrowed, Date.from(Instant.now()), borrowEnd);
        saveBook(changedStatusBook);
    }

}
