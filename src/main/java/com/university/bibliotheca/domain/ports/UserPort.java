package com.university.bibliotheca.domain.ports;

import com.university.bibliotheca.domain.model.User;

import java.util.List;

public interface UserPort {

    void saveUser(User user);

    User findUser(String id);

    List<User> findAllUsers();
}
