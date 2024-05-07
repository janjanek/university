package com.university.bibliotheca.service;

import com.university.bibliotheca.adapter.BookDto;
import com.university.bibliotheca.adapter.mongo.MongoBookAdapter;
import com.university.bibliotheca.domain.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public BookDto findBookDto(String id){
        return mongoBookAdapter.findBook(id).toDto();
    }

    public void testSaveBook(){
        mongoBookAdapter.testSaveBook();
    }
}
