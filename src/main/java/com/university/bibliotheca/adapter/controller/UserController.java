package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest.toDomain());
        return ResponseEntity.ok("Successfully saved user.");
    }

    @GetMapping(path = "/find")
    public UserRequest getUser(@RequestParam String id)  {
       return new UserRequest(userService.findUser(id));
    }

    @GetMapping(path = "/find/all")
    public List<UserRequest> findAllUsers()  {
        return userService.findAllUsers().stream().map(UserRequest::new).collect(Collectors.toList());
    }

}
