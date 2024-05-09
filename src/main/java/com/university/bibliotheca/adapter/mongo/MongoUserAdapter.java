package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.adapter.UserDto;
import com.university.bibliotheca.adapter.mongo.exception.UserNotFoundException;
import com.university.bibliotheca.domain.model.User;
import com.university.bibliotheca.domain.ports.UserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoUserAdapter implements UserPort {

    private UserRepository userRepository;

//    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

    @Autowired
    public MongoUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        UserDto userDto = user.toDto();
        MongoUser mongoUser = new MongoUser(userDto.getId(), userDto.getName(), userDto.getOccupation(), user.getBorrowedBookIds(), user.getReservedBookNames());
        userRepository.save(mongoUser);
    }

    public User findUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
                .toDomain();

    }

    public UserDto findUserDto(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
                .toDomain().toDto();
    }
}
