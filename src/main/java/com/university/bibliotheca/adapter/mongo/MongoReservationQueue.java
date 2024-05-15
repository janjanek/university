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
                        .stream().map(MongoReservation::toMongoReservation)
                        .collect(Collectors.toList())
        );
    }
}

class MongoReservation {
    private String userId;
    private String userName;
    private Occupation occupation;
    private Date reservationDate;


    public MongoReservation(
            String userId,
            String userName,
            Occupation occupation,
            Date reservationDate) {
        this.userId = userId;
        this.userName = userName;
        this.occupation = occupation;
        this.reservationDate = reservationDate;
    }

    public Reservation toDomain() {
        return new Reservation(this.userId, this.userName, this.occupation, this.reservationDate);
    }

    public static MongoReservation toMongoReservation(Reservation reservation) {
        return new MongoReservation(reservation.getUserId(),
                reservation.getUserName(),
                reservation.getOccupation(),
                reservation.getReservationDate()
        );
    }

}