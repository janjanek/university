package com.university.bibliotheca.adapter.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.university.bibliotheca.adapter.UserDto;
import com.university.bibliotheca.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoUserAdapter {

    private UserRepository userRepository;

//    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

    @Autowired
    public MongoUserAdapter(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        UserDto userDto = user.toDto();
        MongoUser mongoUser = new MongoUser(userDto.getId(), userDto.getName(), userDto.getOccupation());
        userRepository.save(mongoUser);
    }

    public User findUser(String id){
        if(userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get().toDomain();
        }
        else return null;
    }

    public UserDto findUserDto(String id){
        if(userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get().toDomain().toDto();
        }
        else return null;
    }
}
