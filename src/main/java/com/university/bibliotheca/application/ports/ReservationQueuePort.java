package com.university.bibliotheca.application.ports;

import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;

import java.util.List;
import java.util.Optional;

public interface ReservationQueuePort {

    void saveReservationQueue(ReservationQueue reservationQueue);

    void deleteReservationQueue(String bookName);

    Optional<ReservationQueue> findQueue(String bookName);

    List<ReservationQueue> findAllReservationQueues();


    Optional<Reservation> findReservation(String bookName, String userId);

}
