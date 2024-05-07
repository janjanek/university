package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.adapter.BookDto;
import com.university.bibliotheca.domain.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoBookAdapter {

    private MongoBookRepository bookRepository;

    @Autowired
    public MongoBookAdapter(MongoBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

//    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");


    public void saveBook(Book book){
        BookDto bookDto = book.toDto();
        MongoBook mongoBook = new MongoBook(bookDto.getId(), bookDto.getAuthor(), bookDto.getName());
        bookRepository.save(mongoBook);
    }

    public Book findBook(String id){
        if(bookRepository.findById(id).isPresent()) {
            return bookRepository.findById(id).get().toDomain();
        }
        else return null;
    }

    public void testSaveBook(){
        bookRepository.save(new MongoBook("1", "Nazwa", "Autor"));
    }
}
