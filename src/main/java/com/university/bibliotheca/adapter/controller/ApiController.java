package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.service.BibliothecaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiController {


    @Autowired
    public ApiController(BibliothecaService bibliothecaService) {
        this.bibliothecaService = bibliothecaService;
    }

    private final BibliothecaService bibliothecaService;
}
