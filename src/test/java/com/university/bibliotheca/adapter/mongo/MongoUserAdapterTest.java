package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.adapter.mongo.MongoUserAdapter;
import com.university.bibliotheca.adapter.mongo.UserRepository;
import com.university.bibliotheca.builders.UserBuilder;
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
public class MongoUserAdapterTest {
    @Autowired
    private UserRepository userRepository;

    private MongoUserAdapter mongoUserAdapter;

    @BeforeEach
    public void init() {
        mongoUserAdapter = new MongoUserAdapter(userRepository);
    }

    @AfterEach()
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Save user in database")
    public void shouldSaveUserInDatabase(){
        //given
        User testUser = UserBuilder.build();

        //when
        mongoUserAdapter.saveUser(testUser);

        //then
        String savedUserId =mongoUserAdapter.findUser(testUser.getId()).getId();

        Assertions.assertThat(testUser.getId()).isEqualTo(savedUserId);
    }

}
