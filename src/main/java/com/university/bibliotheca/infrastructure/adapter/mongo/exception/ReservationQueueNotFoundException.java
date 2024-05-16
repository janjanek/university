package com.university.bibliotheca.infrastructure.adapter.mongo.exception;

public class ReservationQueueNotFoundException extends RuntimeException {
    public ReservationQueueNotFoundException(String bookName) {
        super("There is no reservation for book with name: " + bookName,
                null,
                false,
                false);
    }
}
