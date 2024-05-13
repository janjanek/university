package com.university.bibliotheca.adapter.controller;

import com.university.bibliotheca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/books")
@CrossOrigin("http://localhost:3000/")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(
            BookService bookService
    ) {
        this.bookService = bookService;
    }


    @GetMapping(path = "/{id}")
    public BookRequest getBook(@PathVariable String id) {
        return new BookRequest(bookService.findBook(id));
    }

    @GetMapping(path = "/name/{name}")
    public List<BookRequest> getBooksByName(@PathVariable String name) {
        return bookService.findBooksByName(name).stream().map(BookRequest::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/")
    public List<BookRequest> getAllBooks() {
        return bookService.findAllBooks().stream().map(BookRequest::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/available/{name}")
    public ResponseEntity<BookRequest> getAvailableBook(@PathVariable String name) {
        return bookService.findAvailableBookByName(name).map(book ->
                ResponseEntity.ok().body(new BookRequest(book))
        ).orElse(
                ResponseEntity.notFound().build()
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        if( bookService.deleteBook(id)){
            return ResponseEntity.ok("Succsesfully deleted book!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Book is borrowed, thus cannot be deleted!");
        }
    }
}
