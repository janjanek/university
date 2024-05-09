package com.university.bibliotheca.domain.model;

import com.university.bibliotheca.adapter.UserDto;
import lombok.Value;

import java.util.List;

@Value
public class User {
    String id;
    String name;
    Occupation occupation;
    List<String> borrowedBookIds; // Potrzebne do wypisania pozyczonych ksiazek uzytkownika
    List<String> reservedBookNames; // Potrzebne do wypisania zarezerwowanych ksiazek uzytkownika

//    public User(String id, String name, Occupation occupation) {
//        this.id = id;
//        this.name = name;
//        this.occupation = occupation;
//    }

    public UserDto toDto(){
        return new UserDto(this.id, this.name, this.occupation);
    }
}


