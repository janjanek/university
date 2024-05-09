package com.university.bibliotheca.domain.model;

import com.university.bibliotheca.adapter.BookDto;
import lombok.Value;

import java.util.Date;

@Value
public class Book {
    String id;
    String name;
    String author;

    boolean isBorrowed;
    String borrower;
    Date borrowStart;
    Date borrowEnd;


    public Book(String id, String name, String author, boolean isBorrowed, String borrower ,Date borrowStart, Date borrowEnd) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isBorrowed = isBorrowed;
        this.borrower = borrower;
        this.borrowStart = borrowStart;
        this.borrowEnd = borrowEnd;
    }

    public Book(String id, String name, String author, boolean isBorrowed, String borrower) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isBorrowed = isBorrowed;
        this.borrower = borrower;
        this.borrowStart = null;
        this.borrowEnd = null;
    }

    public Book(String id,String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isBorrowed = false;
        this.borrower = null;
        this.borrowStart = null;
        this.borrowEnd = null;
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
