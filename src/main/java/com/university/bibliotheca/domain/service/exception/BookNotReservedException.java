package com.university.bibliotheca.domain.service.exception;

public class BookNotReservedException extends RuntimeException {
    public BookNotReservedException(String bookName) {
        super("Book with this name is not reserved: " + bookName,
                null,
                false,
                false);
    }
}