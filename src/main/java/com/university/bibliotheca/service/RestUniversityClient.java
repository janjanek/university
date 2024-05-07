package com.university.bibliotheca.service;

import com.university.bibliotheca.domain.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RestUniversityClient implements UniversityClient {

//    @Value("${api_url}")
    @Value("${api_url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public RestUniversityClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable(value = "book")
    public Book getBook(String name){

        try{
            Book response = restTemplate.getForObject(apiUrl, Book.class);
            return response;
        } catch(Exception e) {
            log.warn("Book:" + name + " not found");
            throw e;
        }
    }

}
