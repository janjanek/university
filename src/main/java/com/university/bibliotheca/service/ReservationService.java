package com.university.bibliotheca.service;

import com.university.bibliotheca.adapter.mongo.exception.ReservationQueueNotFoundException;
import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.model.BorrowResult;
import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;
import com.university.bibliotheca.domain.model.ReturnResult;
import com.university.bibliotheca.domain.model.SaveResult;
import com.university.bibliotheca.domain.model.User;
import com.university.bibliotheca.domain.ports.ReservationQueuePort;
import com.university.bibliotheca.service.exception.ReservationNotFoundException;
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
public class ReservationService {

    private BookService bookService;
    private UserService userService;

    private ReservationQueuePort mongoReservationQueueAdapter;

    @Value("${borrow_days}")
    private int BORROW_DAYS;

    @Autowired
    public ReservationService(BookService bookService, UserService userService, ReservationQueuePort mongoReservationQueueAdapter) {
        this.bookService = bookService;
        this.userService = userService;
        this.mongoReservationQueueAdapter = mongoReservationQueueAdapter;
    }

    //1. zarezerwowanie pierwszej z brzegu książki
//2. dorzucenie usera do listy rezerwowych danej książki
//    wyciagnij z bazy ReservatoinQueue i dodaj nowa rezerwacje
//    DONE.
    public BorrowResult borrowBook(String userId, String bookName, Date borrowEnd) {
        if (!isAlreadyBorrowed(userId, bookName)) {
            Optional<Book> bookToBorrow = bookService.findAvailableBookByName(bookName);
            if (bookToBorrow.isPresent()) {
                bookService.changeBorrowStatus(bookToBorrow.get().getId(), true, userId, borrowEnd);

                userService.addBorrowToUser(userId, bookToBorrow.get().getId());
                return BorrowResult.BORROWED;
            } else {
                log.info("[ReservationService] Didn't borrow book for user: " + userId);
                if (isAlreadyReserved(userId, bookName)) {
                    log.info("[ReservationService] Book is already reserved!");
                    return BorrowResult.ALREADY_RESERVED;
                }
                log.info("[ReservationService] User with id: " + userId + " - Making reservation for book: " + bookName);
                reserveBook(userId, bookName);
                return BorrowResult.RESERVED;
            }
        } else {
            return BorrowResult.ALREADY_BORROWED;
        }
    }

    public ReturnResult returnBook(String userId, String bookId) {
        Book returnedBook = bookService.findBook(bookId);
        if (returnedBook.getBorrower() != null && returnedBook.getBorrower().equals(userId)) {
            bookService.changeBorrowStatus(returnedBook.getId(), false, null, null);
            userService.removeBorrowFromUser(userId, bookId);

            try {
                borrowReservedBook(returnedBook);
                return ReturnResult.RETURNED_AND_RESERVED;
            } catch (ReservationQueueNotFoundException | ReservationNotFoundException e) {
                return ReturnResult.RETURNED;
            }
        } else {
            return ReturnResult.NOT_OWNED;
        }
    }

    public SaveResult addBook(Book book) {
        bookService.saveBook(book);
        try {
            borrowReservedBook(book);
            return SaveResult.SAVED_AND_BORROWED;
        } catch (ReservationQueueNotFoundException e) {
            return SaveResult.SAVED;
        }
    }

    public ReservationQueue findReservationQueue(String bookName) {
        return mongoReservationQueueAdapter.findQueue(bookName);
    }

    public List<ReservationQueue> findAllReservationQueues() {
        return mongoReservationQueueAdapter.findAllReservationQueues().stream().map(reservationQueue -> new ReservationQueue(reservationQueue.getName(), sortReservations(reservationQueue.getUserReservations()))).collect(Collectors.toList());
    }

    private void reserveBook(String userId, String bookName) {
        User retrievedUser = userService.findUser(userId);
        Reservation reservation = new Reservation(userId, retrievedUser.getName(), retrievedUser.getOccupation(), Date.from(Instant.now()));

        if (isReservationQueuePresent(bookName)) {
            ReservationQueue modifiedReservationQueue = addReservation(findReservationQueue(bookName), reservation);
            saveReservationQueue(modifiedReservationQueue);
        } else {
            saveReservationQueue(new ReservationQueue(bookName, List.of(reservation)));
        }
        userService.addReservationToUser(retrievedUser, bookName);
    }

    private void borrowReservedBook(Book returnedBook) {
        ReservationQueue reservationQueue = mongoReservationQueueAdapter.findQueue(returnedBook.getName());

        assert reservationQueue != null;
        if (reservationQueue.getUserReservations() != null && !reservationQueue.getUserReservations().isEmpty()) {
            Reservation priorityReservation = findPriorityReservation(reservationQueue.getUserReservations());

            User priorityUser = userService.findUser(priorityReservation.getUserId());

            removeReservationFromQueue(reservationQueue, priorityReservation);
            userService.removeReservationFromUser(priorityUser, returnedBook.getName());

            borrowBook(priorityUser.getId(), returnedBook.getName(), Date.from(Instant.now().plus(BORROW_DAYS, DAYS)));
        } else {
            throw (new ReservationQueueNotFoundException(returnedBook.getName()));
        }
    }

    private void removeReservationFromQueue(ReservationQueue reservationQueue, Reservation reservation) {
        ReservationQueue updatedReservationQueue = removeReservation(reservationQueue, reservation);
        if (!updatedReservationQueue.getUserReservations().isEmpty()) {
            mongoReservationQueueAdapter.saveReservationQueue(updatedReservationQueue);
        } else {
            mongoReservationQueueAdapter.deleteReservationQueue(updatedReservationQueue.getName());
        }
    }


    private Reservation findPriorityReservation(List<Reservation> reservations) {
        return sortReservations(reservations).get(0);
    }

    private List<Reservation> sortReservations(List<Reservation> reservations) {
        //Returns Oldest Reservation
        reservations.sort((o1, o2) -> {
            if (o1.getReservationDate() == null || o2.getReservationDate() == null) {
                return 0;
            } else
                return o1.getReservationDate().compareTo(o2.getReservationDate());
        });

        //Returns Higest Priority Occupation
        reservations.sort((o1, o2) -> {
            if (o1.getOccupation() == null || o2.getOccupation() == null) {
                return 0;
            } else
                return o2.getOccupation().getNumVal().compareTo(o1.getOccupation().getNumVal());
        });

        return reservations;
    }

    private boolean isAlreadyReserved(String userId, String bookName) {
        Reservation retrievedReservation = mongoReservationQueueAdapter.findReservation(bookName, userId);
        return (retrievedReservation != null);
    }

    private boolean isAlreadyBorrowed(String userId, String bookName) {
        User retrievedUser = userService.findUser(userId);
        if (retrievedUser.getBorrowedBookIds() == null) {
            return false;
        } else {
            return userService.findUser(userId).getBorrowedBookIds().stream().map(bookId ->
                    bookService.findBook(bookId).getName()
            ).collect(Collectors.toList())
                    .contains(bookName);
        }
    }

    private void saveReservationQueue(ReservationQueue reservationQueue) {
        mongoReservationQueueAdapter.saveReservationQueue(reservationQueue);
    }

    private boolean isReservationQueuePresent(String bookName) {
        return mongoReservationQueueAdapter.isQueuePresent(bookName);
    }

    private ReservationQueue addReservation(ReservationQueue reservationQueue, Reservation reservation) {
        List<Reservation> reservations = reservationQueue.getUserReservations();
        reservations.add(reservation);

        return new ReservationQueue(reservationQueue.getName(), reservations);
    }

    private ReservationQueue removeReservation(ReservationQueue reservationQueue, Reservation reservation) {
        List<Reservation> reservations = reservationQueue.getUserReservations();
        reservations.remove(reservation);

        return new ReservationQueue(reservationQueue.getName(), reservations);
    }

}

