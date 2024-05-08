package com.university.bibliotheca.builders;

import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.Reservation;

import java.time.Instant;
import java.util.Date;

public class ReservationBuilder {

    public static String userId = "123";
    public static String bookName = "Some book name";
    public static Occupation occupation = Occupation.COMMON_USER;
    public static Date reservationDate = Date.from(Instant.ofEpochSecond(1700000000l)); // 14 November 2023 22:13:20

    public static Reservation build() {
        return new Reservation(
                userId,
                bookName,
                occupation,
                reservationDate
        );
    }

    public static Reservation build(String userId) {
        return new Reservation(
                userId,
                bookName,
                occupation,
                reservationDate
        );
    }

}
