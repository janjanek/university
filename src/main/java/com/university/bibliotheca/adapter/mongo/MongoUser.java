package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static java.util.Collections.emptyList;

@Document(collection = "Users")
public class MongoUser {
    @Id
    private String id;
    private String name;
    private Occupation occupation;

    public MongoUser(String id, String name, Occupation occupation) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
    }

    public User toDomain(){
        return new User(this.id, this.name, this.occupation, emptyList(), emptyList());
    }
}
