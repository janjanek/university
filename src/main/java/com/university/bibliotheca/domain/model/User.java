package com.university.bibliotheca.domain.model;

import com.university.bibliotheca.adapter.UserDto;

public class User {
    private String id;
    private String name;
    private Occupation occupation;

    public User(String id, String name, Occupation occupation) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
    }

    public UserDto toDto(){
        return new UserDto(this.id, this.name, this.occupation);
    }
}


