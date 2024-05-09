package com.university.bibliotheca.domain.ports;

import com.university.bibliotheca.adapter.UserDto;
import com.university.bibliotheca.domain.model.User;

public interface UserPort {

    void saveUser(User user);

    User findUser(String id);

    UserDto findUserDto(String id);
}
