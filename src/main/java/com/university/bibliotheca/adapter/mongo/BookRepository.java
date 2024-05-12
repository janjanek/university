package com.university.bibliotheca.adapter.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public interface BookRepository extends MongoRepository<MongoBook, String> {

    Optional<List<MongoBook>> findByName(String name);

    List<MongoBook> findByNameAndIsBorrowedFalse(String name);

}
