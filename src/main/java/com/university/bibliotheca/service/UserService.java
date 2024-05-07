package com.university.bibliotheca.service;

import com.university.bibliotheca.adapter.UserDto;
import com.university.bibliotheca.adapter.mongo.MongoUserAdapter;
import com.university.bibliotheca.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private MongoUserAdapter mongoUserAdapter;

    @Autowired
    public UserService(MongoUserAdapter mongoUserAdapter){
        this.mongoUserAdapter = mongoUserAdapter;
    }

    public void saveUser(User user){
        mongoUserAdapter.saveUser(user);
    }

    public User findUser(String id){
        return mongoUserAdapter.findUser(id);
    }

    public UserDto findUserDto(String id){
        return mongoUserAdapter.findUserDto(id);
    }
}
