package com.university.bibliotheca.service.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String bookName) {
        super("There is no reservation for book with name: " + bookName,
                null,
                false,
                false);
    }
}