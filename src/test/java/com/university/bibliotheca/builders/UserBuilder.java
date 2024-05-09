package com.university.bibliotheca.builders;

import com.university.bibliotheca.domain.model.Occupation;
import com.university.bibliotheca.domain.model.User;

import java.util.List;

public class UserBuilder {
    public static String id = "test-user-id";
    public static String name = "John Smith";
    public static Occupation occupation = Occupation.STUDENT;
    public static List<String> borrowedBooksIds = List.of("test-book-id","another-test-book-id");
    public static List<String> reservedBookNames = List.of("test-book-name","another-test-book-name");

    public static User build() {
        return new User(
                id,
                name,
                occupation,
                null,
                null
        );
    }

    public static User buildWithReservations() {
        return new User(
                id,
                name,
                occupation,
                borrowedBooksIds,
                reservedBookNames
        );
    }

}
