package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.adapter.mongo.exception.ReservationQueueNotFoundException;
import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;
import com.university.bibliotheca.domain.ports.ReservationQueuePort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class MongoReservationQueueAdapter implements ReservationQueuePort {
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

    public void deleteReservationQueue(String bookName){
        reservationQueueRepository.deleteById(bookName);
    }

    @Nullable
    public ReservationQueue findQueue(String bookName) {
            return reservationQueueRepository
                    .findById(bookName)
                    .orElseThrow(() -> new ReservationQueueNotFoundException(bookName))
                    .toDomain();
    }

    @Nullable
    public List<ReservationQueue> findAllReservationQueues() {
        return reservationQueueRepository
                .findAll().stream().map(MongoReservationQueue::toDomain).collect(Collectors.toList());
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

}
