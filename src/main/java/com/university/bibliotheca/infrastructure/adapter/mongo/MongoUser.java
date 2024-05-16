package com.university.bibliotheca.infrastructure.adapter.mongo;

import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Users")
public class MongoUser {
    @Id
    private String id;
    private String name;
    private Occupation occupation;
    List<String> borrowedBooksIds;
    List<String> reservedBookNames;

    public MongoUser(String id, String name, Occupation occupation, List<String> borrowedBooksIds, List<String> reservedBookNames) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
        this.borrowedBooksIds = borrowedBooksIds;
        this.reservedBookNames = reservedBookNames;
    }

    public User toDomain(){
        return new User(this.id, this.name, this.occupation, borrowedBooksIds, reservedBookNames);
    }
}
