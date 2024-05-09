package com.university.bibliotheca.domain.model;

import com.university.bibliotheca.adapter.UserDto;
import lombok.Value;

import java.util.List;

@Value
public class User {
    String id;
    String name;
    Occupation occupation;
    List<String> borrowedBookIds;
    List<String> reservedBookNames;

//    public User(String id, String name, Occupation occupation) {
//        this.id = id;
//        this.name = name;
//        this.occupation = occupation;
//    }

    public UserDto toDto(){
        return new UserDto(this.id, this.name, this.occupation);
    }
}


