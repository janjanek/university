package com.university.bibliotheca.builders;

import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class ReservationQueueBuilder {

    public static String name = "test-book";
    public static Reservation reservation1 = ReservationBuilder.build();
    public static Reservation reservation2 = ReservationBuilder.build("another-user-id");
    public static List<Reservation> userReservations = List.of(reservation1, reservation2);


    public static ReservationQueue build() {
        return new ReservationQueue(
                name,
                userReservations
        );
    }
}
