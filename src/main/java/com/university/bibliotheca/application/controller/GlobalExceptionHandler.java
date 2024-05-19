package com.university.bibliotheca.application.controller;

import com.university.bibliotheca.infrastructure.adapter.mongo.exception.AvailableBookNotFoundException;
import com.university.bibliotheca.infrastructure.adapter.mongo.exception.BookNotFoundException;
import com.university.bibliotheca.infrastructure.adapter.mongo.exception.ReservationQueueNotFoundException;
import com.university.bibliotheca.infrastructure.adapter.mongo.exception.UserNotFoundException;
import com.university.bibliotheca.domain.service.exception.BookAlreadyBorrowedException;
import com.university.bibliotheca.domain.service.exception.BookAlreadyReservedException;
import com.university.bibliotheca.domain.service.exception.BookNotBorrowedException;
import com.university.bibliotheca.domain.service.exception.BookNotReservedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, BookNotFoundException.class, AvailableBookNotFoundException.class, ReservationQueueNotFoundException.class})
    public ResponseEntity<String> handleDatabaseException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found!");
    }

    @ExceptionHandler({BookAlreadyBorrowedException.class, BookAlreadyReservedException.class, BookNotBorrowedException.class, BookNotReservedException.class })
    public ResponseEntity<String> handleServiceExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found!");
    }

    @ExceptionHandler({ParseException.class})
    public ResponseEntity<String> handleParserException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data parsed incorrectly!");
    }
}
