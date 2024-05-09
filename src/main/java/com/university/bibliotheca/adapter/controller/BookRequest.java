package com.university.bibliotheca.adapter.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.university.bibliotheca.adapter.BookDto;
import com.university.bibliotheca.domain.model.Book;

public class BookRequest {
    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "author")
    private String author;


    public BookRequest(String id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public BookRequest(BookDto bookDto){
        this.id = bookDto.getId();
        this.name = bookDto.getName();
        this.author = bookDto.getAuthor();
    }

    public BookRequest(Book book){
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
    }

    public Book toDomain() {
        return new Book(this.id, this.name, this.author);
    }
}
