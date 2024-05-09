package com.university.bibliotheca.service;

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
import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class ReservationServiceTest {
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

    @AfterEach()
    public void cleanUp(){
        reservationQueueRepository.deleteAll();
        userRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Should borrow book for a user")
    public void borrowBookForUserTest(){
        //given
        Book testBook = BookBuilder.build();
        User testUser = UserBuilder.build();

        bookService.saveBook(testBook);
        userService.saveUser(testUser);

        //when
        BorrowResult borrowResult = reservationService.borrowBook(testUser.getId(), testBook.getName(), Date.from(Instant.ofEpochSecond(1800000000))); //15 January 2027 08:00:00

        //then
        assertEquals(BorrowResult.BORROWED, borrowResult);
    }


    @Test
    @DisplayName("Should reserve book for a user")
    public void reserveBookForUserTest(){
        //given
        Book testBook = BookBuilder.buildBorrowed();
        User testUser = UserBuilder.build();

        bookService.saveBook(testBook);
        userService.saveUser(testUser);

        //when
        BorrowResult borrowResult = reservationService.borrowBook(testUser.getId(), testBook.getName(), Date.from(Instant.ofEpochSecond(1800000000))); //15 January 2027 08:00:00


        //then
        assertEquals(BorrowResult.RESERVED, borrowResult);
    }


    @Test
    @DisplayName("Should return status, that book is already reserved by a user")
    public void reserveAlreadyReservedBookForUserTest(){
        //given
        Book testBook = BookBuilder.buildBorrowed();
        User testUser = UserBuilder.build();

        bookService.saveBook(testBook);
        userService.saveUser(testUser);

        //when
        reservationService.borrowBook(testUser.getId(), testBook.getName(), Date.from(Instant.ofEpochSecond(1800000000))); //15 January 2027 08:00:00
        //and
        BorrowResult borrowResult = reservationService.borrowBook(testUser.getId(), testBook.getName(), Date.from(Instant.ofEpochSecond(1800000000))); //15 January 2027 08:00:00

        //then
        assertEquals(BorrowResult.ALREADY_RESERVED, borrowResult);
    }

    @Test
    @DisplayName("Should return status, that book is already reserved by a user")
    public void findPriority(){
        //given
        Book testBook = BookBuilder.buildBorrowed();
        User testUser = UserBuilder.build();

        User diffUser = new User("another-id", "A", Occupation.STUDENT, null, null);

        bookService.saveBook(testBook);
        userService.saveUser(testUser);
        userService.saveUser(diffUser);

        //when
        reservationService.borrowBook(testUser.getId(), testBook.getName(), Date.from(Instant.ofEpochSecond(1800000000))); //15 January 2027 08:00:00
        reservationService.borrowBook(diffUser.getId(), testBook.getName(), Date.from(Instant.ofEpochSecond(1800000000)) );

//        reservationService.findPriorityReservation();

        //and
        BorrowResult borrowResult = reservationService.borrowBook(testUser.getId(), testBook.getName(), Date.from(Instant.ofEpochSecond(1800000000))); //15 January 2027 08:00:00

        //then
        assertEquals("nie", "ha");
    }
}
