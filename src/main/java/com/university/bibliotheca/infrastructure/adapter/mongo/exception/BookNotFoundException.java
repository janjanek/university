package com.university.bibliotheca.infrastructure.adapter.mongo.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String bookId) {
        super("There is no book with an ID: " + bookId,
                null,
                false,
                false);
    }
}
