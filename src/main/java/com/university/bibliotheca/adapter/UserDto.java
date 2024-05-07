package com.university.bibliotheca.adapter;

import com.university.bibliotheca.domain.model.Occupation;
import lombok.Value;

@Value
public class UserDto {
    String id;
    String name;
    Occupation occupation;
}
