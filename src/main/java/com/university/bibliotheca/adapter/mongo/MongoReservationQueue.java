package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "Reservations")
public class MongoReservationQueue {
    @Id
    private String name;
    private List<MongoReservation> userReservations;

    public MongoReservationQueue(String name, List<MongoReservation> userReservations) {
        this.name = name;
        this.userReservations = userReservations;
    }

    public ReservationQueue toDomain() {
        List<Reservation> reservations =
                userReservations.stream()
                        .map(MongoReservation::toDomain)
                        .collect(Collectors.toList());
        return new ReservationQueue(this.name, reservations);
    }

    public static MongoReservationQueue toMongoReservationQueue(ReservationQueue reservationQueue) {
        return new MongoReservationQueue(
                reservationQueue.getName(),
                reservationQueue.getUserReservations()
                        .stream().map(userReservation -> MongoReservation.toMongoReservation(userReservation))
                        .collect(Collectors.toList())
        );
    }
}

class MongoReservation {
//    @Id
    private String userId;
    private String bookName;
    private Occupation occupation;
    private Date reservationDate;


    public MongoReservation(
            String userId,
            String bookName,
            Occupation occupation,
            Date reservationDate) {
        this.userId = userId;
        this.bookName = bookName;
        this.occupation = occupation;
        this.reservationDate = reservationDate;
    }

//    public MongoReservation(Reservation reservation) {
//        this.userId = reservation.getUserId();
//        this.bookName = reservation.getBookName();
//        this.occupation = reservation.getOccupation();
//        this.reservationDate = reservation.getReservationDate();
//    }

    public Reservation toDomain() {
        return new Reservation(this.userId, this.bookName, this.occupation, this.reservationDate);
    }

    public static MongoReservation toMongoReservation(Reservation reservation) {
        return new MongoReservation(reservation.getUserId(),
                reservation.getBookName(),
                reservation.getOccupation(),
                reservation.getReservationDate()
        );
    }

}