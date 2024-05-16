package com.university.bibliotheca.domain.service;

import com.university.bibliotheca.infrastructure.adapter.mongo.MongoUserAdapter;
import com.university.bibliotheca.infrastructure.adapter.mongo.UserRepository;
import com.university.bibliotheca.builders.BookBuilder;
import com.university.bibliotheca.builders.UserBuilder;
import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.model.User;
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
public class UserServiceTest {
    private UserService userService;
    private MongoUserAdapter mongoUserAdapter;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void init() {
        mongoUserAdapter = new MongoUserAdapter(userRepository);
        userService = new UserService(mongoUserAdapter);
    }

    @AfterEach()
    public void cleanUp(){
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("Should add reservation for a user")
    public void addReservationToUser(){
        //given
        Book testBook = BookBuilder.build();
        User testUser = UserBuilder.build();

        userService.saveUser(testUser);

        //when
        userService.addReservationToUser(testUser, testBook.getName());

        //then
        String resultBookName = userService.findUser(testUser.getId()).getReservedBookNames().get(0);
        Assertions.assertThat(resultBookName).isEqualTo(testBook.getName());
    }

    @Test
    @DisplayName("Should add borrowed book to user")
    public void addBorrowToUser(){
        //given
        Book testBook = BookBuilder.build();
        User testUser = UserBuilder.build();

        userService.saveUser(testUser);

        //when
        userService.addBorrowToUser(testUser.getId(), testBook.getId());

        //then
        String resultBookId = userService.findUser(testUser.getId()).getBorrowedBookIds().get(0);
        Assertions.assertThat(resultBookId).isEqualTo(testBook.getId());
    }

    @Test
    @DisplayName("Should remove reserved book from user")
    public void removeReservationFromUser(){
        //given
        Book testBook = BookBuilder.build();
        User testUser = UserBuilder.build();

        userService.saveUser(testUser);

        //when
        userService.addReservationToUser(testUser, testBook.getName());
        String resultBookName = userService.findUser(testUser.getId()).getReservedBookNames().get(0);
//        Assertions.assertThat(resultBookName).isEqualTo(testBook.getName());

        User updatedUser = userService.findUser(testUser.getId());
        //and
        userService.removeReservationFromUser(updatedUser, testBook.getName());

        //then
        System.out.println(resultBookName);
        User resultUser = userService.findUser(testUser.getId());
       org.junit.jupiter.api.Assertions.assertTrue(resultUser.getReservedBookNames().isEmpty());
    }


}
