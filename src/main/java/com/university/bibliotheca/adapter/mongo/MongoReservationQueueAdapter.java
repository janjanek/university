package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;
import com.university.bibliotheca.domain.ports.ReservationQueuePort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Component
public class MongoReservationQueueAdapter implements ReservationQueuePort {
    private ReservationQueueRepository reservationQueueRepository;

    @Autowired
    public MongoReservationQueueAdapter(ReservationQueueRepository reservationQueueRepository) {
        this.reservationQueueRepository = reservationQueueRepository;
    }

    public void saveReservationQueue(ReservationQueue reservationQueue) {
        reservationQueueRepository.save(MongoReservationQueue.toMongoReservationQueue(reservationQueue));
    }

    public void deleteReservationQueue(String bookName) {
        reservationQueueRepository.deleteById(bookName);
    }

    @Nullable
    public Optional<ReservationQueue> findQueue(String bookName) {
        return reservationQueueRepository.findById(bookName).map(MongoReservationQueue::toDomain);
    }

    @Nullable
    public List<ReservationQueue> findAllReservationQueues() {
        return reservationQueueRepository
                .findAll().stream().map(MongoReservationQueue::toDomain).collect(Collectors.toList());
    }


    @Nullable
    public Optional<Reservation> findReservation(String bookName, String userId) {
        return reservationQueueRepository.findById(bookName).flatMap(reservationQueue -> reservationQueue.toDomain().getUserReservations()
                .stream()
                .filter(reservation -> userId.equals(reservation.getUserId()))
                .findAny());
    }

}
