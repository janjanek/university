package com.university.bibliotheca.service;

import com.university.bibliotheca.adapter.mongo.MongoReservationQueueAdapter;
import com.university.bibliotheca.adapter.mongo.exception.AvailableBookNotFoundException;
import com.university.bibliotheca.adapter.mongo.exception.ReservationNotFoundException;
import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.model.BorrowResult;
import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;
import com.university.bibliotheca.domain.model.ReturnResult;
import com.university.bibliotheca.domain.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class ReservationService {

    private BookService bookService;
    private UserService userService;

    private MongoReservationQueueAdapter mongoReservationQueueAdapter;

    @Autowired
    public ReservationService(BookService bookService, UserService userService, MongoReservationQueueAdapter mongoReservationQueueAdapter) {
        this.bookService = bookService;
        this.userService = userService;
        this.mongoReservationQueueAdapter = mongoReservationQueueAdapter;
    }

    //1. zarezerwowanie pierwszej z brzegu książki
//2. dorzucenie usera do listy rezerwowych danej książki
//    wyciagnij z bazy ReservatoinQueue i dodaj nowa rezerwacje
//    DONE.
    public BorrowResult borrowBook(String userId, String bookName, Date borrowEnd) {
        try {
            Book bookToBorrow = bookService.findAvailableBookByName(bookName);
            bookService.changeBorrowStatus(bookToBorrow.getId(), true, userId, borrowEnd);
            userService.addBorrowToUser(userId, bookToBorrow.getId());
            return BorrowResult.BORROWED;
        } catch (AvailableBookNotFoundException e) {
            log.info("[ReservationService] Didn't borrow book for user: " + userId + ", making reservation for book: " + bookName);
            if (isAlreadyReserved(userId, bookName)) {
                log.info("[ReservationService] Book is already reserved!");
                return BorrowResult.ALREADY_RESERVED;
            }
            reserveBook(userId, bookName);
            return BorrowResult.RESERVED;
        }
    }

    public ReturnResult returnBook(String userId, String bookId) {
        Book returnedBook = bookService.findBook(bookId);
        bookService.changeBorrowStatus(returnedBook.getId(), false, null, null);
        userService.removeBorrowFromUser(userId, bookId);

        try {
            findPriorityReservation();
            //Zwrotka Returned and someone Borrowed from Reserved List
        } catch (ReservationNotFoundException ignored){
            //Zwrotka Returned and Not Borrowed from Reserved List
        }

        return null;
    }

    public void findPriorityReservation(){
        mongoReservationQueueAdapter.findPriorityReservation("test-book-name");
    }

    private void reserveBook(String userId, String bookName) {
        User retrievedUser = userService.findUser(userId);
        Reservation reservation = new Reservation(userId, bookName, retrievedUser.getOccupation(), Date.from(Instant.now()));

        if (isReservationQueuePresent(bookName)) {
            ReservationQueue modifiedReservationQueue = addReservation(findReservationQueue(bookName), reservation);
            saveReservationQueue(modifiedReservationQueue);
        } else {
            saveReservationQueue(new ReservationQueue(bookName, List.of(reservation)));
        }
        userService.addReservationToUser(retrievedUser, bookName);
    }

    private boolean isAlreadyReserved(String userId, String bookName) {
        Reservation retrievedReservation = mongoReservationQueueAdapter.findReservation(bookName, userId);
        return (retrievedReservation != null);
    }

    private void saveReservationQueue(ReservationQueue reservationQueue) {
        mongoReservationQueueAdapter.saveReservationQueue(reservationQueue);
    }

    private boolean isReservationQueuePresent(String bookName) {
        return mongoReservationQueueAdapter.isQueuePresent(bookName);
    }

    private ReservationQueue findReservationQueue(String bookName) {
        return mongoReservationQueueAdapter.findQueue(bookName);
    }

    private ReservationQueue addReservation(ReservationQueue reservationQueue, Reservation reservation) {
        List<Reservation> reservations = reservationQueue.getUserReservations();
        reservations.add(reservation);

        return new ReservationQueue(reservationQueue.getName(), reservations);
    }
}

