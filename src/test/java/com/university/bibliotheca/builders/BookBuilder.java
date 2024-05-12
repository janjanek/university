package com.university.bibliotheca.builders;

import com.university.bibliotheca.domain.model.Book;

import java.time.Instant;
import java.util.Date;

public class BookBuilder {

    public static String id = "test-book-id";
    public static String name = "test-book-name";
    public static String author = "test-autor";
    public static boolean isBorrowed = true;
    public static String borrower = "test-user-id";
    public static Date borrowStart = Date.from(Instant.ofEpochSecond(1700000000l)); // 14 November 2023 22:13:20
    public static Date borrowEnd = Date.from(Instant.ofEpochSecond(1750000000l)); //15 June 2025 15:06:40

    public static Book build() {
        return new Book(
                id,
                name,
                author,
                false,
                null,
                null,
                null
        );
    }

    public static Book buildBorrowed() {
        return new Book(
                id,
                name,
                author,
                isBorrowed,
                borrower,
                borrowStart,
                borrowEnd
        );
    }
}
