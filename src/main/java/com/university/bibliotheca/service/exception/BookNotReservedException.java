package com.university.bibliotheca.service.exception;

public class BookNotReservedException extends RuntimeException {
    public BookNotReservedException(String bookName) {
        super("Book is already reserved with name: " + bookName,
                null,
                false,
                false);
    }
}