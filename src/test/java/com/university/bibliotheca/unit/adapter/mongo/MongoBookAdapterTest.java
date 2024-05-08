package com.university.bibliotheca.unit.adapter.mongo;

import com.university.bibliotheca.adapter.mongo.MongoBookAdapter;
import com.university.bibliotheca.adapter.mongo.BookRepository;
import com.university.bibliotheca.builders.BookBuilder;
import com.university.bibliotheca.domain.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MongoBookAdapterTest {
    @Autowired
    private BookRepository bookRepository;

    private MongoBookAdapter mongoBookAdapter;

    @BeforeEach
    public void init() {
        mongoBookAdapter = new MongoBookAdapter(bookRepository);
    }

    @Test
    @DisplayName("Save book in database")
    public void shouldSaveBookInDatabase(){
        //given
        Book testBook = BookBuilder.build();

        //when
        mongoBookAdapter.saveBook(testBook);

        //then
        String saveBookId = Objects.requireNonNull(mongoBookAdapter.findBook(testBook.getId())).getId();

        assertEquals(testBook.getId(), saveBookId);
    }
}
