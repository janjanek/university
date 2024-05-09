package com.university.bibliotheca.adapter.mongo.exception;

public class AvailableBookNotFoundException extends RuntimeException {
    public AvailableBookNotFoundException(String name) {
        super("There is no available book with name: " + name,
                null,
                false,
                false);
    }
}