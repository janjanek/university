package com.university.bibliotheca.service;

import com.university.bibliotheca.adapter.mongo.BookRepository;
import com.university.bibliotheca.adapter.mongo.MongoBookAdapter;
import com.university.bibliotheca.builders.BookBuilder;
import com.university.bibliotheca.builders.UserBuilder;
import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.model.User;
import com.university.bibliotheca.domain.ports.BookPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class BookServiceTest {
    private BookService bookService;
    private BookPort mongoBookAdapter;

    @Autowired
    private BookRepository bookRepository;


    @BeforeEach
    public void init() {
        mongoBookAdapter = new MongoBookAdapter(bookRepository);
        bookService = new BookService(mongoBookAdapter);
    }

    @AfterEach()
    public void cleanUp() {
        bookRepository.deleteAll();
    }



    @Test
    @DisplayName("Should find available book by given name")
    public void shouldFindAvailableBookByName(){
        //given
        Book testBook = BookBuilder.build();
        User testUser = UserBuilder.build();
        mongoBookAdapter.saveBook(testBook);

        //when
        String resultBookId = bookService.findAvailableBookByName(testBook.getName()).getId();

        //then
        Assertions.assertThat(resultBookId).isEqualTo(testBook.getId());
    }
}
