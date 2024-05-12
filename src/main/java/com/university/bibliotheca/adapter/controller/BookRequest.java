package com.university.bibliotheca.adapter.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.university.bibliotheca.domain.model.Book;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class BookRequest {
    @JsonProperty(value = "id")
    private String id;
    @NotNull
    @JsonProperty(value = "name")
    private String name;
    @NotNull
    @JsonProperty(value = "author")
    private String author;
    @JsonProperty(value = "isBorrowed")
    private boolean isBorrowed = false;
    @JsonProperty(value = "borrower")
    private String borrower = null;
    @JsonProperty(value = "borrowStart")
    private Date borrowStart = null;
    @JsonProperty(value = "borrowEnd")
    private Date borrowEnd = null;

//
//    public BookRequest(String id, String name, String author) {
//        this.id = id;
//        this.name = name;
//        this.author = author;
//    }

    public BookRequest(Book book){
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.isBorrowed = book.isBorrowed();
        this.borrower = book.getBorrower();
        this.borrowStart = book.getBorrowStart();
        this.borrowEnd = book.getBorrowEnd();
    }

    public BookRequest(String id, String name, String author, boolean isBorrowed, String borrower, Date borrowStart, Date borrowEnd) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isBorrowed = isBorrowed;
        this.borrower = borrower;
        this.borrowStart = borrowStart;
        this.borrowEnd = borrowEnd;
    }

    public Book toDomain() {
        return new Book(this.id, this.name, this.author);
    }
}
