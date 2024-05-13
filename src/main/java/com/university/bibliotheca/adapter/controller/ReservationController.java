package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.adapter.mongo.exception.ReservationQueueNotFoundException;
import com.university.bibliotheca.domain.model.BorrowResult;
import com.university.bibliotheca.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping("/reservations")
@CrossOrigin("http://localhost:3000/")
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

    @PostMapping(path = "/")
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

    @DeleteMapping(path = "/{userId}/{bookName}")
    public ResponseEntity<String> deleteReservation(@PathVariable String userId, @PathVariable String bookName) {
            if(reservationService.removeUserReservationFromQueue(userId, bookName)){
                return ResponseEntity.ok("Successfully removed reservation.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User has no reservation for this book!");
            }
        }

    @GetMapping(path = "/{bookName}")
    public ReservationQueueRequest findReservationQueue(@PathVariable String bookName) {
        return new ReservationQueueRequest(reservationService.findReservationQueue(bookName).orElseThrow(
                () -> new ReservationQueueNotFoundException(bookName)
        ));
    }

    @GetMapping(path = "/")
    public List<ReservationQueueRequest> findAllReservationQueues() {
        return reservationService.findAllReservationQueues().stream().map(ReservationQueueRequest::new).collect(Collectors.toList());
    }

}
