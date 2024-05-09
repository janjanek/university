package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.adapter.mongo.exception.ReservationNotFoundException;
import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class MongoReservationQueueAdapter {
    private ReservationQueueRepository reservationQueueRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    public MongoReservationQueueAdapter(ReservationQueueRepository reservationQueueRepository) {
        this.reservationQueueRepository = reservationQueueRepository;
    }

    public void saveReservationQueue(ReservationQueue reservationQueue) {
        reservationQueueRepository.save(MongoReservationQueue.toMongoReservationQueue(reservationQueue));
    }

    @Nullable
    public ReservationQueue findQueue(String bookName) {
            return reservationQueueRepository
                    .findById(bookName)
                    .orElseThrow(() -> new ReservationNotFoundException(bookName))
                    .toDomain();
    }

    @Nullable
    public Reservation findReservation(String bookName, String userId) {
        if (reservationQueueRepository.findById(bookName).isPresent()) {
            return reservationQueueRepository.findById(bookName).get().toDomain().getUserReservations()
                    .stream()
                    .filter(reservation -> userId.equals(reservation.getUserId()))
                    .findAny()
                    .orElse(null);
        } else {
            log.info("[MongoReservationQueueAdapter] queue not found by bookName: " + bookName);
            return null;
        }
    }


    public boolean isQueuePresent(String bookName) {
        return reservationQueueRepository.findById(bookName).isPresent();
    }

    public Reservation findPriorityReservation(String bookName) {

        ReservationQueue reservationQueue = findQueue(bookName);
        List<Reservation> reservations = reservationQueue.getUserReservations();

        //Returns Oldest Reservation
        reservations.sort((o1, o2) -> {
            if (o1.getReservationDate() == null || o2.getReservationDate() == null){
                return 0;
            } else
            return o1.getReservationDate().compareTo(o2.getReservationDate());
        });

        //Returns Higest Priority Occupation
        reservations.sort((o1, o2) -> {
            if (o1.getOccupation() == null || o2.getOccupation() == null){
                return 0;
            } else
                return o2.getOccupation().getNumVal().compareTo(o1.getOccupation().getNumVal());
        });

        return reservations.get(0);
    }

}
