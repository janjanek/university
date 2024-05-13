package com.university.bibliotheca.adapter.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static java.util.Collections.emptyList;

public class UserRequest {
    @JsonProperty(value = "id")
    private String id;
    @NotNull
    @JsonProperty(value = "name")
    private String name;
    @NotNull
    @JsonProperty(value = "occupation")
    private Occupation occupation;

    @JsonProperty(value = "borrowedBookIds")
    private List<String> borrowedBookIds; // Potrzebne do wypisania pozyczonych ksiazek uzytkownika
    @JsonProperty(value = "reservedBookNames")
    private List<String> reservedBookNames;


    public UserRequest(String id, String name, Occupation occupation) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
    }

    public UserRequest(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.occupation = user.getOccupation();
        this.borrowedBookIds = user.getBorrowedBookIds();
        this.reservedBookNames = user.getReservedBookNames();
    }


    public User toDomain(){
        return new User(this.id, this.name, this.occupation, emptyList(), emptyList());
    }
}
