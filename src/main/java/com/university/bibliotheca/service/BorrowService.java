package com.university.bibliotheca.service;

import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.model.User;

public class BorrowService {
    private BookService bookService;



    public void borrowBook(User user, String name) {
        if(bookService.isBookAvailable(name)){
            

        } else{

        }
    }

    public void addUserToQueue(User user, String name){

    }
}
