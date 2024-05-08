package com.university.bibliotheca.domain.model;

import com.university.bibliotheca.adapter.BookDto;
import lombok.Value;

import java.time.Instant;
import java.util.Date;

@Value
public class Book {
    String id;
    String name;
    String author;

    String reader;
    boolean isBorrowed;
    Date borrowStart;
    Date borrowEnd;


    public Book(String id, String name, String author, String reader, boolean isBorrowed, Date borrowStart, Date borrowEnd) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.reader = reader;
        this.isBorrowed = isBorrowed;
        this.borrowStart = borrowStart;
        this.borrowEnd = borrowEnd;
    }

    public Book(String id,String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.reader = "reader";
        this.isBorrowed = false;
        this.borrowStart = Date.from(Instant.EPOCH);
        this.borrowEnd = Date.from(Instant.EPOCH);
    }


    @Override
    public String toString() {
        return "BookDto{" +
                "name='" + name + '\'' +
                ", auhtor='" + author + '\'' +
                '}';
    }

    public BookDto toDto(){
        return new BookDto(id, name, author);
    }
}
