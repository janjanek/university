package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    public void addUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest.toDomain());
    }

    @GetMapping(path = "/find")
    public UserRequest getUser(@RequestParam String id)  {
       return new UserRequest(userService.findUserDto(id));
    }

}
