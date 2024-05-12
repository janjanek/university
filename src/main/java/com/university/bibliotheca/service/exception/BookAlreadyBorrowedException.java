package com.university.bibliotheca.service.exception;

public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(String bookId) {
        super("Book is already borrowed with an ID: " + bookId,
                null,
                false,
                false);
    }
}
