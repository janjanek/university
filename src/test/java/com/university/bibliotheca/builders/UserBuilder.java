package com.university.bibliotheca.builders;

import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.User;

import java.util.List;

public class UserBuilder {
    public static String id = "123";
    public static String name = "John Smith";
    public static Occupation occupation = Occupation.STUDENT;
    public static List<String> borrowedBooksIds = List.of("123","456");
    public static List<String> reservedBookNames = List.of("Some book name","Another book name");

    public static User build() {
        return new User(
                id,
                name,
                occupation,
                borrowedBooksIds,
                reservedBookNames
        );
    }
}
