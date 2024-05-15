package com.university.bibliotheca.service;

import com.university.bibliotheca.adapter.controller.BookRequest;
import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.model.BorrowResult;
import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;
import com.university.bibliotheca.domain.model.ReturnResult;
import com.university.bibliotheca.domain.model.SaveResult;
import com.university.bibliotheca.domain.model.User;
import com.university.bibliotheca.domain.ports.ReservationQueuePort;
import com.university.bibliotheca.domain.ports.UserPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Log4j2
@Service
public class WaitingListService {


    private BookService bookService;
    private UserService userService;
    private ReservationService reservationService;

    private ReservationQueuePort mongoReservationQueueAdapter;
    private UserPort mongoUserAdapter;


    @Value("${borrow_days}")
    private int BORROW_DAYS;

    @Autowired
    public WaitingListService(BookService bookService, UserService userService, ReservationService reservationService, ReservationQueuePort mongoReservationQueueAdapter, UserPort mongoUserAdapter) {
        this.bookService = bookService;
        this.userService = userService;
        this.reservationService = reservationService;
        this.mongoReservationQueueAdapter = mongoReservationQueueAdapter;
        this.mongoUserAdapter = mongoUserAdapter;
    }

    public BorrowResult borrowBook(String userId, String bookName, Date borrowEnd) {
        if (!isAlreadyBorrowed(userId, bookName)) {
            Optional<Book> bookToBorrow = bookService.findAvailableBookByName(bookName);
            if (bookToBorrow.isPresent()) {
                bookService.changeBorrowStatus(bookToBorrow.get().getId(), true, userId, Date.from(Instant.now()), borrowEnd);

                userService.addBorrowToUser(userId, bookToBorrow.get().getId());
                return BorrowResult.BORROWED;
            } else {
                log.info("[ReservationService] Didn't borrow book for user: " + userId);
                if (isAlreadyReserved(userId, bookName)) {
                    log.info("[ReservationService] Book is already reserved!");
                    return BorrowResult.ALREADY_RESERVED;
                }
                log.info("[ReservationService] User with id: " + userId + " - Making reservation for book: " + bookName);
                reservationService.reserveBook(userId, bookName);
                return BorrowResult.RESERVED;
            }
        } else {
            return BorrowResult.ALREADY_BORROWED;
        }
    }

    public SaveResult addBook(Book book) {
        bookService.saveBook(book);
        if (borrowReservedBook(book)) {
            return SaveResult.SAVED_AND_BORROWED;
        } else {
            return SaveResult.SAVED;
        }
    }

    public ReturnResult returnBook(String userId, String bookId) {
        Book returnedBook = bookService.findBook(bookId);
        if (returnedBook.getBorrower() != null && returnedBook.getBorrower().equals(userId)) {
            bookService.changeBorrowStatus(returnedBook.getId(), false, null, null, null);
            userService.removeBorrowFromUser(userId, bookId);


            if (borrowReservedBook(returnedBook)) {
                return ReturnResult.RETURNED_AND_BORROWED;
            } else {
                return ReturnResult.RETURNED;
            }
        } else {
            return ReturnResult.NOT_OWNED;
        }
    }

    public List<Book> findAllBooks() {
        List<Book> books = bookService .findAllBooks();
        List<BookRequest> bookRequests = books.stream().map(book -> {
            if(!book.getBorrower().isEmpty()) {
                User user = userService.findUser(book.getBorrower());
                new BookRequest(book, user.getName());
            }
                return new BookRequest(book);
                }
        ).collect(Collectors.toList());
        return books;
    }
    public boolean deleteUser(String userId) {
        User user = mongoUserAdapter.findUser(userId);
        if(user.getBorrowedBookIds().isEmpty()) {
            user.getReservedBookNames().forEach(it ->
                    reservationService.removeUserReservationFromQueue(userId, it)
            );
            mongoUserAdapter.deleteUser(userId);
            return true;
        } else {
            return false;
        }
    }

    private boolean borrowReservedBook(Book returnedBook) {
        Optional<ReservationQueue> reservationQueueOptional = mongoReservationQueueAdapter.findQueue(returnedBook.getName());

        if (reservationQueueOptional.isPresent()) {
            ReservationQueue reservationQueue = reservationQueueOptional.get();
            if (!reservationQueue.getUserReservations().isEmpty()) {
                Reservation priorityReservation = reservationService.findPriorityReservation(reservationQueue.getUserReservations());

                reservationService.removeReservationFromQueueAndUser(reservationQueue, priorityReservation, priorityReservation.getUserId(), returnedBook.getName());
                borrowBook(priorityReservation.getUserId(), returnedBook.getName(), Date.from(Instant.now().plus(BORROW_DAYS, DAYS)));
                return true;
            }
        }
        return false;
    }

    private boolean isAlreadyReserved(String userId, String bookName) {
        return mongoReservationQueueAdapter.findReservation(bookName, userId).isPresent();
    }

    private boolean isAlreadyBorrowed(String userId, String bookName) {
        User retrievedUser = userService.findUser(userId);
        if (retrievedUser.getBorrowedBookIds() == null) {
            return false;
        } else {
            return bookService.findBorrowedBookForUser(userId, bookName).isPresent();
        }
    }
}
