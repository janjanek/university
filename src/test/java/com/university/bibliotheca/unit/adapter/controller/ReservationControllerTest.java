package com.university.bibliotheca.unit.adapter.controller;

import com.university.bibliotheca.adapter.mongo.BookRepository;
import com.university.bibliotheca.adapter.mongo.MongoBookAdapter;
import com.university.bibliotheca.adapter.mongo.MongoReservationQueueAdapter;
import com.university.bibliotheca.adapter.mongo.MongoUserAdapter;
import com.university.bibliotheca.adapter.mongo.ReservationQueueRepository;
import com.university.bibliotheca.adapter.mongo.UserRepository;
import com.university.bibliotheca.builders.BookBuilder;
import com.university.bibliotheca.builders.UserBuilder;
import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.model.BorrowResult;
import com.university.bibliotheca.domain.model.User;
import com.university.bibliotheca.service.BookService;
import com.university.bibliotheca.service.ReservationService;
import com.university.bibliotheca.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ReservationControllerTest {

    private ReservationService reservationService;

    private BookService bookService;
    private UserService userService;

    private MongoBookAdapter mongoBookAdapter;
    private MongoUserAdapter mongoUserAdapter;
    private MongoReservationQueueAdapter mongoReservationQueueAdapter;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationQueueRepository reservationQueueRepository;



    @BeforeEach
    public void init() {
        mongoBookAdapter = new MongoBookAdapter(bookRepository);
        bookService = new BookService(mongoBookAdapter);
        mongoUserAdapter = new MongoUserAdapter(userRepository);
        userService = new UserService(mongoUserAdapter);
        mongoReservationQueueAdapter = new MongoReservationQueueAdapter(reservationQueueRepository);

        reservationService = new ReservationService(bookService, userService, mongoReservationQueueAdapter);
    }

    @Test
    @DisplayName("Should borrow book for a user")
    public void borrowBookForAUSerTest(){
        //given
        Book testBook = BookBuilder.build();
        User testUser = UserBuilder.build();

        bookService.saveBook(testBook);
        userService.saveUser(testUser);

        //when
        BorrowResult borrowResult = reservationService.borrowBook(testUser.getId(), testBook.getName(), Date.from(Instant.ofEpochSecond(1800000000))); //15 January 2027 08:00:00

        //then
        assertEquals(BorrowResult.RESERVED, borrowResult);

    }
}
