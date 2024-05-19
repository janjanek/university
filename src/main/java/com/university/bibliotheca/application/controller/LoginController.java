package com.university.bibliotheca.application.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin("http://localhost:3000/")
public class LoginController {

@GetMapping("/login")
    public String login() {
        return "Logged in successfully";
    }
}
