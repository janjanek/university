package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.service.BookService;
import com.university.bibliotheca.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
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



}
