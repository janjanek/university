package com.university.bibliotheca.unit.adapter.mongo;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.university.bibliotheca.adapter.mongo.MongoBookAdapter;
import com.university.bibliotheca.adapter.mongo.BookRepository;
import com.university.bibliotheca.builders.BookBuilder;
import com.university.bibliotheca.domain.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
//@SpringBootTest
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
        String saveBookId =mongoBookAdapter.findBook(testBook.getId()).getId();

        assertEquals(testBook.getId(), saveBookId);
    }

    @Test
    public void test(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        // when
        mongoTemplate.save(objectToSave, "collection");

        // then
        assertEquals(mongoTemplate.findAll(DBObject.class, "collection").get(0), objectToSave);
    }
}
