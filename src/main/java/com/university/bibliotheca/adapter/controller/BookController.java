package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(
            BookService bookService
    ) {
        this.bookService = bookService;
    }

    @PostMapping(path = "/add")
    public void addBook(@RequestBody BookRequest bookRequest) {
        bookService.saveBook(bookRequest.toDomain());
    }

    @GetMapping(path = "/find")
    public BookRequest getBook(@RequestParam String id) {
       return new BookRequest(bookService.findBookDto(id));
    }


    @RequestMapping(path = "/testAdd")
    public void testAddBook() {
        bookService.testSaveBook();
        System.out.println("Udało się");
    }
}
