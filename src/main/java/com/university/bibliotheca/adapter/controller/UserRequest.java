package com.university.bibliotheca.adapter.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.university.bibliotheca.adapter.UserDto;
import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.User;

public class UserRequest {
    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "occupation")
    private Occupation occupation;


    public UserRequest(String id, String name, Occupation occupation) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
    }

    public UserRequest(UserDto userDto){
        this.id = userDto.getId();
        this.name = userDto.getName();
        this.occupation = userDto.getOccupation();
    }

    public User toDomain(){
        return new User(this.id, this.name, this.occupation);
    }
}
