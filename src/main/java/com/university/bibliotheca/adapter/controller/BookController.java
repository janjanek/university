package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.service.BookService;
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
@RequestMapping("/book")
@CrossOrigin("http://localhost:3000/")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(
            BookService bookService
    ) {
        this.bookService = bookService;
    }

    @PostMapping(path = "/add") // /books maybe put?
    public void addBook(@RequestBody BookRequest bookRequest) {
        bookService.saveBook(bookRequest.toDomain());
    }

    @GetMapping(path = "/find") // books/{id}
    public BookRequest getBook(@RequestParam String id) {
        return new BookRequest(bookService.findBook(id));
    }

    @GetMapping(path = "/find/all") // /books
    public List<BookRequest> findAllBooks() {
        return bookService.findAllBooks().stream().map(BookRequest::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/findAvailable") // /books?available=true&name=nazwa
    public ResponseEntity<BookRequest> getAvailableBook(@RequestParam String name) {
        return bookService.findAvailableBookByName(name).map(optionalBook ->
                ResponseEntity.ok().body(new BookRequest(optionalBook))
        ).orElse(
                ResponseEntity.notFound().build()
        );

    }

}
