package com.university.bibliotheca.domain.model;

import com.university.bibliotheca.adapter.BookDto;

public class Book {
    private String id;
    private String name;
    private String author;

    public Book(String id,String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
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
