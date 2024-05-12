package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.domain.model.Book;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Books")
public class MongoBook {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String name;
    private String author;
    private boolean isBorrowed;
    private String borrower;
    private Date borrowStart;
    private Date borrowEnd;



    public MongoBook(String id, String name, String author, boolean isBorrowed, String borrower, Date borrowStart, Date borrowEnd) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isBorrowed = isBorrowed;
        this.borrower = borrower;
        this.borrowStart = borrowStart;
        this.borrowEnd = borrowEnd;
    }
//
//    public MongoBook(String name, String author, boolean isBorrowed, String borrower, Date borrowStart, Date borrowEnd) {
//        this.name = name;
//        this.author = author;
//        this.isBorrowed = isBorrowed;
//        this.borrower = borrower;
//        this.borrowStart = borrowStart;
//        this.borrowEnd = borrowEnd;
//    }

    public Book toDomain(){
        return new Book(this.id, this.name, this.author, this.isBorrowed, this.borrower, this.borrowStart, this.borrowEnd);
    }
}
