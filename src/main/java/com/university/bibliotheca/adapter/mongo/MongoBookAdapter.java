package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.adapter.BookDto;
import com.university.bibliotheca.adapter.mongo.exception.BookNotFoundException;
import com.university.bibliotheca.domain.model.Book;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MongoBookAdapter {

    private BookRepository bookRepository;

    @Autowired
    public MongoBookAdapter(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

//    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");


    public void saveBook(Book book){
        BookDto bookDto = book.toDto();
        MongoBook mongoBook = new MongoBook(bookDto.getId(), bookDto.getAuthor(), bookDto.getName());
        bookRepository.save(mongoBook);
    }

    @Nullable
    public Book findBook(String id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id))
                .toDomain();
    }

    public boolean isBookAvailable(String name){
        return true;
    }

}
