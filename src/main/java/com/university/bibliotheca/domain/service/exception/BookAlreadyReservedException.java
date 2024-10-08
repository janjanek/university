package com.university.bibliotheca.domain.service.exception;

public class BookAlreadyReservedException extends RuntimeException {
    public BookAlreadyReservedException(String bookName) {
        super("Book is already reserved with name: " + bookName,
                null,
                false,
                false);
    }
}
