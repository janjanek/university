package com.university.bibliotheca.adapter.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface BookRepository extends MongoRepository<MongoBook, String> {

}
