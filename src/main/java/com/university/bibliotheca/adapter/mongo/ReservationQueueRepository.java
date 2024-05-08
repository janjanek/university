package com.university.bibliotheca.adapter.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ReservationQueueRepository extends MongoRepository<MongoReservationQueue, String> {
//    @Query()
//    MongoReservation findPriorityReservation(String name);
}

//db.collection.aggregate([
//        // Match documents with the specified occupation values
//        { $match: { occupation: { $in: ["SIEMA", "WITAM", "CZESC"] } } },
//
//        // Project necessary fields
//        { $project: { _id: 1, occupation: 1, reservationDate: 1 } },
//
//        // Add a new field for priority mapping
//        {
//        $addFields: {
//        priority: {
//        $switch: {
//        branches: [
//        { case: { $eq: ["$occupation", "SIEMA"] }, then: 3 }, // Najwyższy priorytet
//        { case: { $eq: ["$occupation", "WITAM"] }, then: 2 }, // Średni priorytet
//        { case: { $eq: ["$occupation", "CZESC"] }, then: 1 }, // Najniższy priorytet
//        ],
//default: null // W przypadku innych wartości enuma
//        }
//        }
//        }
//        },
//
//        // Sort documents by reservationDate in ascending order and priority in descending order
//        { $sort: { reservationDate: 1, priority: -1 } },
//
//        // Group by null and take the first document in the group
//        { $group: { _id: null, document: { $first: "$$ROOT" } } },
//
//        // Project the fields of the first document
//        { $project: { _id: "$document._id", occupation: "$document.occupation", reservationDate: "$document.reservationDate" } }
//        ])