package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.adapter.mongo.exception.AvailableBookNotFoundException;
import com.university.bibliotheca.adapter.mongo.exception.BookNotFoundException;
import com.university.bibliotheca.adapter.mongo.exception.ReservationNotFoundException;
import com.university.bibliotheca.adapter.mongo.exception.UserNotFoundException;
import com.university.bibliotheca.service.exception.BookAlreadyBorrowedException;
import com.university.bibliotheca.service.exception.BookAlreadyReservedException;
import com.university.bibliotheca.service.exception.BookNotBorrowedException;
import com.university.bibliotheca.service.exception.BookNotReservedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, BookNotFoundException.class, AvailableBookNotFoundException.class, ReservationNotFoundException.class})
    public ResponseEntity<String> handleDatabaseException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found!");
    }

    @ExceptionHandler({BookAlreadyBorrowedException.class, BookAlreadyReservedException.class, BookNotBorrowedException.class, BookNotReservedException.class})
    public ResponseEntity<String> handleServiceExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found!");
    }
}
