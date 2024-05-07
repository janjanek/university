package com.university.bibliotheca.service;

import com.university.bibliotheca.domain.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BibliothecaService {

    private final RestUniversityClient restUniversityClient;

    @Autowired
    public BibliothecaService(RestUniversityClient restUniversityClient) {
        this.restUniversityClient = restUniversityClient;
    }

    public RestUniversityClient getRestUniversityClient() {
        return restUniversityClient;
    }

    public Book getBook(String name){
        return restUniversityClient.getBook(name);
    }

    public void addBook(Book book){

    }
}
