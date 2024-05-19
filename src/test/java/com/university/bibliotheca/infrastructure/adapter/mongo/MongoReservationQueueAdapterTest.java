package com.university.bibliotheca.infrastructure.adapter.mongo;

import com.university.bibliotheca.builders.ReservationBuilder;
import com.university.bibliotheca.builders.ReservationQueueBuilder;
import com.university.bibliotheca.domain.model.Reservation;
import com.university.bibliotheca.domain.model.ReservationQueue;
import com.university.bibliotheca.application.ports.ReservationQueuePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MongoReservationQueueAdapterTest {
    @Autowired
    private ReservationQueueRepository reservationQueueRepository;

    private ReservationQueuePort mongoReservationQueueAdapter;

    @BeforeEach
    public void init() {
        mongoReservationQueueAdapter = new MongoReservationQueueAdapter(reservationQueueRepository);
    }

    @Test
    @DisplayName("Save ReservationQueue in database")
    public void shouldSaveReservationQueueInDatabase(){
        //given
        ReservationQueue reservationQueue = ReservationQueueBuilder.build();

        //when
        mongoReservationQueueAdapter.saveReservationQueue(reservationQueue);

        //then
        String savedReservationQueueName = mongoReservationQueueAdapter.findQueue(reservationQueue.getName()).get().getName();

        assertEquals(reservationQueue.getName(), savedReservationQueueName);
    }

    @Test
    @DisplayName("Find saved reservation in Queue from database")
    public void shouldFindReservationInQueueFromDatabase(){
        //given
        ReservationQueue reservationQueue = ReservationQueueBuilder.build();
        Reservation reservation = ReservationBuilder.build();

        //when
        mongoReservationQueueAdapter.saveReservationQueue(reservationQueue);

        //then
        Reservation savedReservation = mongoReservationQueueAdapter.findReservation(reservationQueue.getName(), reservation.getUserId()).orElse(null);

        assertEquals(savedReservation, reservation);
    }

}
