package com.university.bibliotheca.domain.ports;

import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;

public interface ReservationQueuePort {

    void saveReservationQueue(ReservationQueue reservationQueue);

    ReservationQueue findQueue(String bookName);

    Reservation findReservation(String bookName, String userId);

    boolean isQueuePresent(String bookName);


}
