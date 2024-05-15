package com.university.bibliotheca.adapter.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.university.bibliotheca.domain.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class BookRequest {
    @JsonProperty(value = "id")
    private String id;
    @NotBlank(message ="Name of book may not be empty.")
    @JsonProperty(value = "name")
    private String name;
    @NotEmpty(message = "Author may not be empty.")
    @JsonProperty(value = "author")
    private String author;
    @JsonProperty(value = "isBorrowed")
    private boolean isBorrowed = false;
    @JsonProperty(value = "borrower")
    private String borrower = null;
    @JsonProperty(value = "borrowStart")
    private String borrowStart = null;
    @JsonProperty(value = "borrowEnd")
    private String borrowEnd = null;

    public BookRequest(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.isBorrowed = book.isBorrowed();
        this.borrower = book.getBorrower();
        if (book.getBorrowStart() != null) {
            this.borrowStart = book.getBorrowStart().toString();
        }
        if (book.getBorrowEnd() != null) {
            this.borrowEnd = book.getBorrowEnd().toString();
        }
    }

    public BookRequest(String id, String name, String author, boolean isBorrowed, String borrower, String borrowStart, String borrowEnd) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isBorrowed = isBorrowed;
        this.borrower = borrower;
        this.borrowStart = borrowStart;
        this.borrowEnd = borrowEnd;
    }

    public Book toDomain() {
        if(this.name != null){
            return new Book(this.id, this.name, this.author);
        } else{
            return new Book(this.id, "", this.author);
        }
    }
}
