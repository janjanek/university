package com.university.bibliotheca.infrastructure.adapter.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends MongoRepository<MongoUser, String> {
}
