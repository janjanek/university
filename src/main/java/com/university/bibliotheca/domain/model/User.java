package com.university.bibliotheca.domain.model;

import lombok.Value;

import java.util.List;

@Value
public class User {
    String id;
    String name;
    Occupation occupation;
    List<String> borrowedBookIds; // Potrzebne do wypisania pozyczonych ksiazek uzytkownika
    List<String> reservedBookNames; // Potrzebne do wypisania zarezerwowanych ksiazek uzytkownika

}


