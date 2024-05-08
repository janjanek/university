package com.university.bibliotheca.adapter.mongo.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("There is no active user with an ID: " + userId,
                null,
                false,
                false);
    }
}
