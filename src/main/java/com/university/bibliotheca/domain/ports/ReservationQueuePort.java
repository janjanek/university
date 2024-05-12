package com.university.bibliotheca.domain.ports;

import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;

import java.util.List;

public interface ReservationQueuePort {

    void saveReservationQueue(ReservationQueue reservationQueue);

    void deleteReservationQueue(String bookName);

    ReservationQueue findQueue(String bookName);

    List<ReservationQueue> findAllReservationQueues();


    Reservation findReservation(String bookName, String userId);

    boolean isQueuePresent(String bookName);


}
