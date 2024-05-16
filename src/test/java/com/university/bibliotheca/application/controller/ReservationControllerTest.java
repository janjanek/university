package com.university.bibliotheca.application.controller;

import com.university.bibliotheca.builders.BookBuilder;
import com.university.bibliotheca.builders.UserBuilder;
import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.model.BorrowResult;
import com.university.bibliotheca.domain.model.User;
import com.university.bibliotheca.domain.service.BookService;
import com.university.bibliotheca.domain.service.ReservationService;
import com.university.bibliotheca.domain.service.UserService;
import com.university.bibliotheca.domain.service.WaitingListService;
import com.university.bibliotheca.infrastructure.adapter.mongo.BookRepository;
import com.university.bibliotheca.infrastructure.adapter.mongo.MongoBookAdapter;
import com.university.bibliotheca.infrastructure.adapter.mongo.MongoReservationQueueAdapter;
import com.university.bibliotheca.infrastructure.adapter.mongo.MongoUserAdapter;
import com.university.bibliotheca.infrastructure.adapter.mongo.ReservationQueueRepository;
import com.university.bibliotheca.infrastructure.adapter.mongo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    private WaitingListService waitingListService;

    private ReservationController reservationController;

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

    @Autowired
    private TestRestTemplate restTemplate;


    @LocalServerPort
    private int port;


    @BeforeEach
    public void init() {
        mongoBookAdapter = new MongoBookAdapter(bookRepository);
        bookService = new BookService(mongoBookAdapter);
        mongoUserAdapter = new MongoUserAdapter(userRepository);
        userService = new UserService(mongoUserAdapter);
        mongoReservationQueueAdapter = new MongoReservationQueueAdapter(reservationQueueRepository);

        reservationService = new ReservationService(userService, mongoReservationQueueAdapter);
        waitingListService = new WaitingListService(bookService, userService, reservationService, mongoReservationQueueAdapter, mongoUserAdapter);

        reservationController = new ReservationController(reservationService, waitingListService);
    }

    @AfterEach()
    public void cleanUp() {
        reservationQueueRepository.deleteAll();
        userRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Should borrow book for a user")
    public void borrowBookForUserTest() {
        //given
        Book testBook = BookBuilder.build();
        User testUser = UserBuilder.build();

        bookService.saveBook(testBook);
        userService.saveUser(testUser);

        //when
        BorrowResult borrowResult = waitingListService.borrowBook(testUser.getId(), testBook.getName(), Date.from(Instant.ofEpochSecond(1800000000))); //15 January 2027 08:00:00

        //then
        assertEquals(BorrowResult.BORROWED, borrowResult);
    }


    @Test
    @DisplayName("Should find book by id")
    public void findBookById() {
        //given
        Book testBook = BookBuilder.buildBorrowed();
        User testUser = UserBuilder.build();

        bookService.saveBook(testBook);
        userService.saveUser(testUser);

        //when
        BookRequest bookRequest = restTemplate.getForObject(
                "http://localhost:" + port + "/books/" + testBook.getId(),
                BookRequest.class
        );

        //then
        assert bookRequest != null;
        assertEquals(testBook.getName(), bookRequest.toDomain().getName());
    }


    @Test
    @DisplayName("Should find book by name")
    public void findBookByName() {
        //given
        Book testBook = BookBuilder.buildBorrowed();
        User testUser = UserBuilder.build();

        bookService.saveBook(testBook);
        userService.saveUser(testUser);

        //when
        BookRequest[] bookRequests = restTemplate.getForObject(
                "http://localhost:" + port + "/books/name/" + testBook.getName(),
                BookRequest[].class
        );

        //then
        assert bookRequests != null;
        assertEquals(testBook.getId(), bookRequests[0].toDomain().getId());
    }

    @Test
    @DisplayName("Should return book for user")
    public void shouldReturnBook() {
        //given
        Book testBook = BookBuilder.build();
        User testUser = UserBuilder.build();

        bookService.saveBook(testBook);
        userService.saveUser(testUser);

        //when
        ResponseEntity<String> reservationResult = restTemplate.postForEntity(
                "http://localhost:" + port + "/reservations/?userId=" + testUser.getId() + "&bookName=" + testBook.getName(),
                null,
                String.class
        );
        assert reservationResult != null;
        assertEquals(HttpStatus.OK, reservationResult.getStatusCode());

        //and
        ResponseEntity<String> returnResult = restTemplate.postForEntity(
                "http://localhost:" + port + "/books/return?userId=" + testUser.getId() + "&bookId=" + testBook.getId(),
                null,
                String.class
        );

        //then
        assert returnResult != null;
        assertEquals(HttpStatus.OK, returnResult.getStatusCode());
        //and
        assertTrue(mongoUserAdapter.findUser(testUser.getId()).getBorrowedBookIds().isEmpty());
    }

}
