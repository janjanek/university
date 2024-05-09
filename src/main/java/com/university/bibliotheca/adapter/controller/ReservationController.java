package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.domain.model.BorrowResult;
import com.university.bibliotheca.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;

    @Value("${borrow_days}")
    private int BORROW_DAYS;

    @Autowired
    public ReservationController(
            ReservationService reservationService
    ) {
        this.reservationService = reservationService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addReservation(@RequestParam String userId, @RequestParam String bookName) {
        BorrowResult status = reservationService.borrowBook(userId, bookName, Date.from(Instant.now().plus(BORROW_DAYS, DAYS)));

        return switch (status) {
            case BORROWED -> ResponseEntity.ok("Successfully borrowed book.");
            case ALREADY_RESERVED -> ResponseEntity.status(status.getStatus()).body("This book is already reserved by user!");
            case RESERVED -> ResponseEntity.status(status.getStatus()).body("No books available. You've been successfully added to reservation list.");
            case ALREADY_BORROWED -> ResponseEntity.status(status.getStatus()).body("This books is already borrowed by user!");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error occurred");
        };
    }

    @PostMapping(path = "return")
    public ResponseEntity<String> returnBook(@RequestParam String userId, @RequestParam String bookId){
        HttpStatus status = reservationService.returnBook(userId, bookId).getStatus(); // moze starczyc wypchniecie notFound exception
        return switch (status){
            case OK -> ResponseEntity.ok("Successfully returned book.");
            case CREATED -> ResponseEntity.status(status).body("Successfully returned book, and gave it to next person from reservation list.");
            case CONFLICT ->  ResponseEntity.status(status).body("Book is not owned by user.");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error occurred");
        };
    }
}
