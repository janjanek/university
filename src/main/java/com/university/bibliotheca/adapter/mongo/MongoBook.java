package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.domain.model.Book;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Books")
public class MongoBook {
    @Id private String id;
    private String name;
    private String author;


    public MongoBook(String id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Book toDomain(){
        return new Book(this.id, this.name, this.author);
    }
}
