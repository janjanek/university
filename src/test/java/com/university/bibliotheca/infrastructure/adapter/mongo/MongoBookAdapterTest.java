package com.university.bibliotheca.infrastructure.adapter.mongo;

import com.university.bibliotheca.builders.BookBuilder;
import com.university.bibliotheca.domain.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MongoBookAdapterTest {
    @Autowired
    private BookRepository bookRepository;

    private MongoBookAdapter mongoBookAdapter;

    @BeforeEach
    public void init() {
        mongoBookAdapter = new MongoBookAdapter(bookRepository);
    }

    @AfterEach()
    public void cleanUp(){
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Save book in database")
    public void shouldSaveBookInDatabase(){
        //given
        Book testBook = BookBuilder.build();

        //when
        mongoBookAdapter.saveBook(testBook);

        //then
        String saveBookId =mongoBookAdapter.findBook(testBook.getId()).getId();

        assertEquals(testBook.getId(), saveBookId);
    }

}
