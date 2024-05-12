package com.university.bibliotheca.adapter.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationQueueRepository extends MongoRepository<MongoReservationQueue, String> {

}