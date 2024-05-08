package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ReservationController(
            ReservationService reservationService
    ) {
        this.reservationService = reservationService;
    }

    @PostMapping(path = "/add")
    public void addReservation(@RequestParam String userName, @RequestParam String bookName) {
        reservationService.borrowBook(userName, bookName, Date.from(Instant.now().plus(30, DAYS)));
    }
}
