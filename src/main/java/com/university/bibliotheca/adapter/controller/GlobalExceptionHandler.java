package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.adapter.mongo.exception.AvailableBookNotFoundException;
import com.university.bibliotheca.adapter.mongo.exception.BookNotFoundException;
import com.university.bibliotheca.adapter.mongo.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, BookNotFoundException.class, AvailableBookNotFoundException.class})
    public ResponseEntity<String> handleDatabaseException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found!");
    }
}
