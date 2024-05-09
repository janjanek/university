package com.university.bibliotheca.adapter.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends MongoRepository<MongoBook, String> {

    Optional<List<MongoBook>> findByName(String name);

    Optional<List<MongoBook>> findByNameAndIsBorrowedFalse(String name);

}
