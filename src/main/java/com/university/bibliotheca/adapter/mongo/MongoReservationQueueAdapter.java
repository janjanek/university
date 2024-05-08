package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MongoReservationQueueAdapter {
    private ReservationQueueRepository reservationQueueRepository;

    @Autowired
    public MongoReservationQueueAdapter(ReservationQueueRepository reservationQueueRepository) {
        this.reservationQueueRepository = reservationQueueRepository;
    }

    public void saveReservationQueue(ReservationQueue reservationQueue) {
        reservationQueueRepository.save(MongoReservationQueue.toMongoReservationQueue(reservationQueue));
    }

    @Nullable
    public ReservationQueue findQueue(String bookName) {
        if (reservationQueueRepository.findById(bookName).isPresent()) {
            return reservationQueueRepository
                    .findById(bookName).get().toDomain();
        } else {
            log.info("Find reservation not found by id: " + bookName);
            return null;
        }
    }

    @Nullable
    public Reservation findReservation(String id, String userId) {
        if (reservationQueueRepository.findById(id).isPresent()) {
            return reservationQueueRepository.findById(id).get().toDomain().getUserReservations()
                    .stream()
                    .filter(reservation -> userId.equals(reservation.getUserId()))
                    .findAny()
                    .orElse(null);
        } else {
            log.info("Find reservation not found by id: " + id);
            return null;
        }
    }


    public boolean isQueuePresent(String bookName) {
        return reservationQueueRepository.findById(bookName).isPresent();
    }

    public Reservation findPriorityReservation() {
//        Query query = new Query();
//        query.with(new Sort(new Order(Direction.ASC, "createdDate").ignoreCase());
//        return mongoTemplate.find(query, Notifications.class);
//        mongoResRepo.findAll
//        reservationQueueRepository.findPriorityReservation("abc"); //TODO: Query po: Rezerwację z najwyzszym ENUM priorytetem i najstarszą datą. Potrzebne dla wyciągania pierwszeństwa wypożczenia sposrod rezerwujacych. Mozliwe ze tez pojdzie do serwisu reservatoinService
        return null;
    }

}
