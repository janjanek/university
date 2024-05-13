package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.service.BookService;
import com.university.bibliotheca.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@CrossOrigin("http://localhost:3000/")
public class LibraryManagementController {

    private ReservationService reservationService;
    private BookService bookService;

    @Autowired
    public LibraryManagementController(
            ReservationService reservationService,
            BookService bookService
    ) {
        this.reservationService = reservationService;
        this.bookService = bookService;
    }

    @PutMapping(path = "/")
    public ResponseEntity<String> addBook(@RequestBody BookRequest bookRequest){
        HttpStatus status = reservationService.addBook(bookRequest.toDomain()).getStatus(); // moze starczyc wypchniecie notFound exception
        return switch (status) {
            case OK -> ResponseEntity.ok("Successfully created book.");
            case CREATED -> ResponseEntity.status(status).body("Successfully created book, and gave it to the reservee");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error occurred");
        };
    }

    @PostMapping(path = "/return")
    public ResponseEntity<String> returnBook(@RequestParam String userId, @RequestParam String bookId) {
        HttpStatus status = reservationService.returnBook(userId, bookId).getStatus();
        return switch (status) {
            case OK -> ResponseEntity.ok("Successfully returned book.");
            case CREATED -> ResponseEntity.status(status).body("Successfully returned book, and gave it to next person from reservation list.");
            case CONFLICT -> ResponseEntity.status(status).body("Book is not owned by user.");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error occurred");
        };
    }

}
