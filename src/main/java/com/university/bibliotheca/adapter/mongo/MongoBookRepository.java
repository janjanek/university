package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.ports.BookRepository;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongoBookRepository extends MongoRepository<MongoBook, String>, BookRepository {

}
