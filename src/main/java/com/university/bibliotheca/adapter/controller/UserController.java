package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @PutMapping(path = "/")
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest.toDomain());
        return ResponseEntity.ok("Successfully saved user.");
    }

    @GetMapping(path = "/{id}")
    public UserRequest getUser(@PathVariable String id) {
        return new UserRequest(userService.findUser(id));
    }

    @GetMapping(path = "/")
    public List<UserRequest> findAllUsers() {
        return userService.findAllUsers().stream().map(UserRequest::new).collect(Collectors.toList());
    }
//TODO: Handle delete user to delete all his reservations and return all books
    @DeleteMapping(path = "/{id}" )
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
         userService.deleteUser(id);
         return ResponseEntity.ok("Succsessfully deleted user");
    }

}
