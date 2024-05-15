package com.university.bibliotheca.service;

import com.university.bibliotheca.adapter.mongo.exception.ReservationQueueNotFoundException;
import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;
import com.university.bibliotheca.domain.model.User;
import com.university.bibliotheca.domain.ports.ReservationQueuePort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ReservationService {

    private UserService userService;

    private ReservationQueuePort mongoReservationQueueAdapter;

    @Value("${borrow_days}")
    private int BORROW_DAYS;

    @Autowired
    public ReservationService(UserService userService, ReservationQueuePort mongoReservationQueueAdapter) {
        this.userService = userService;
        this.mongoReservationQueueAdapter = mongoReservationQueueAdapter;
    }

    public boolean removeUserReservationFromQueue(String userId, String bookName) {
        ReservationQueue reservationQueue = findReservationQueue(bookName).orElseThrow(
                () -> new ReservationQueueNotFoundException(bookName));
        Optional<Reservation> reservationOptional = reservationContainsUser(reservationQueue.getUserReservations(), userId);

        if (reservationOptional.isPresent()) {
            removeReservationFromQueueAndUser(reservationQueue, reservationOptional.get(), userId, bookName);
            return true;
        } else {
            return false;
        }
    }

    public Optional<ReservationQueue> findReservationQueue(String bookName) {
        return mongoReservationQueueAdapter.findQueue(bookName);
    }

    public List<ReservationQueue> findAllReservationQueues() {
        return mongoReservationQueueAdapter.findAllReservationQueues().stream().map(reservationQueue -> new ReservationQueue(reservationQueue.getName(), sortReservations(reservationQueue.getUserReservations()))).collect(Collectors.toList());
    }

    public void reserveBook(String userId, String bookName) {
        User retrievedUser = userService.findUser(userId);
        Reservation reservation = new Reservation(userId, retrievedUser.getName(), retrievedUser.getOccupation(), Date.from(Instant.now()));

        Optional<ReservationQueue> optionalBookQueue = findReservationQueue(bookName);
        if (optionalBookQueue.isPresent()) {
            ReservationQueue modifiedReservationQueue = addReservation(optionalBookQueue.get(), reservation);
            saveReservationQueue(modifiedReservationQueue);
        } else {
            saveReservationQueue(new ReservationQueue(bookName, List.of(reservation)));
        }
        userService.addReservationToUser(retrievedUser, bookName);
    }

    public Reservation findPriorityReservation(List<Reservation> reservations) {
        return sortReservations(reservations).get(0);
    }

    public void removeReservationFromQueueAndUser(ReservationQueue reservationQueue, Reservation reservation, String userId, String bookName){
        removeReservationFromQueue(reservationQueue, reservation);
        User user = userService.findUser(userId);
        userService.removeReservationFromUser(user, bookName);
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

    private void removeReservationFromQueue(ReservationQueue reservationQueue, Reservation reservation) {
        ReservationQueue updatedReservationQueue = removeReservation(reservationQueue, reservation);
        if (!updatedReservationQueue.getUserReservations().isEmpty()) {
            mongoReservationQueueAdapter.saveReservationQueue(updatedReservationQueue);
        } else {
            mongoReservationQueueAdapter.deleteReservationQueue(updatedReservationQueue.getName());
        }
    }

    private Optional<Reservation> reservationContainsUser(List<Reservation> reservations, String userId) {
        return reservations.stream().filter(reservation -> reservation.getUserId().equals(userId)).findAny();
    }

    private void saveReservationQueue(ReservationQueue reservationQueue) {
        mongoReservationQueueAdapter.saveReservationQueue(reservationQueue);
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

