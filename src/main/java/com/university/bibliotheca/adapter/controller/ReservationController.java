package com.university.bibliotheca.adapter.controller;

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
        HttpStatus status = reservationService.borrowBook(userId, bookName, Date.from(Instant.now().plus(BORROW_DAYS, DAYS))).getStatus();

        switch(status){
            case OK:
                return ResponseEntity.ok("Successfully borrowed book.");
            case CONFLICT:
                return ResponseEntity.status(status).body("This book is already reserved by you.");
            case CREATED:
                return ResponseEntity.status(status).body("No books available. You've been successfully added to reservation list.");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error occurred");
        }
    }

    @PostMapping(path = "return")
    public ResponseEntity<String> returnBook(@RequestParam String userId, @RequestParam String bookId){
//        HttpStatus status = reservationService.returnBook(userId, bookId); // moze starczyc wypchniecie notFound exception
        return null;
    }
}
