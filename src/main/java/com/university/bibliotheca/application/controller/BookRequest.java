package com.university.bibliotheca.application.controller;

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
    @JsonProperty(value = "userName")
    private String userName = null;
    @JsonProperty(value = "borrowStart")
    private String borrowStart = null;
    @JsonProperty(value = "borrowEnd")
    private String borrowEnd = null;

    public BookRequest(Book book, String userName) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.isBorrowed = book.isBorrowed();
        this.borrower = book.getBorrower();
        if(userName != null){
            this.userName = userName;
        }
        if(book.getBorrowStart() != null) {
            this.borrowStart = book.getBorrowStart().toString();
        }
        if (book.getBorrowEnd() != null) {
            this.borrowEnd = book.getBorrowEnd().toString();
        }
    }

    public BookRequest(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.isBorrowed = book.isBorrowed();
        this.borrower = book.getBorrower();
        this.userName = null;
        if(book.getBorrowStart() != null) {
            this.borrowStart = book.getBorrowStart().toString();
        }
        if (book.getBorrowEnd() != null) {
            this.borrowEnd = book.getBorrowEnd().toString();
        }
    }

    public BookRequest() {
    }

    public Book toDomain() {
        if(this.name != null){
            return new Book(this.id, this.name, this.author);
        } else{
            return new Book(this.id, "", this.author);
        }
    }
}
