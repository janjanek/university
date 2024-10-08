package com.university.bibliotheca.domain.service.exception;

public class BookNotBorrowedException extends RuntimeException {
    public BookNotBorrowedException(String bookId) {
        super("Book is already borrowed with ID: " + bookId,
                null,
                false,
                false);
    }
}