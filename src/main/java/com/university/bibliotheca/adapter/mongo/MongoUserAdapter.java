package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.adapter.mongo.exception.UserNotFoundException;
import com.university.bibliotheca.domain.model.User;
import com.university.bibliotheca.domain.ports.UserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MongoUserAdapter implements UserPort {

    private UserRepository userRepository;

    @Autowired
    public MongoUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        MongoUser mongoUser = new MongoUser(user.getId(), user.getName(), user.getOccupation(), user.getBorrowedBookIds(), user.getReservedBookNames());
        userRepository.save(mongoUser);
    }

    public User findUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
                .toDomain();

    }

    public List<User> findAllUsers() {
        return userRepository.findAll().stream().map(mongoUser -> mongoUser.toDomain()).collect(Collectors.toList());
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
