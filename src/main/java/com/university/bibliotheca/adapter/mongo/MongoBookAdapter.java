package com.university.bibliotheca.adapter.mongo;

import com.university.bibliotheca.adapter.mongo.exception.BookNotFoundException;
import com.university.bibliotheca.domain.model.Book;
import com.university.bibliotheca.domain.ports.BookPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Component
public class MongoBookAdapter implements BookPort {

    private BookRepository bookRepository;

    @Autowired
    public MongoBookAdapter(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

//    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");


    public void saveBook(Book book) {
        MongoBook mongoBook = new MongoBook(book.getId(), book.getName(), book.getAuthor(), book.isBorrowed(), book.getBorrower(), book.getBorrowStart(), book.getBorrowEnd());

        bookRepository.save(mongoBook);
    }

    public Book findBook(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id))
                .toDomain();
    }

    public void deleteBook(String id){
        bookRepository.deleteById(id);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll().stream().map(mongoBook -> mongoBook.toDomain()).collect(Collectors.toList());
    }

    public List<Book> findBooksByName(String name) {
        List<MongoBook> retrievedBooks = bookRepository.findByName(name)
                .orElseThrow(() -> new BookNotFoundException(name));

        if (!retrievedBooks.isEmpty()) {
            return retrievedBooks.stream()
                    .map(MongoBook::toDomain)
                    .collect(Collectors.toList());
        } else {
            throw (new BookNotFoundException(name));
        }
    }


    public Optional<Book> findAvailableBookByName(String name) {
        List<MongoBook> retrievedBooks = bookRepository.findByNameAndIsBorrowedFalse(name);

        if (!retrievedBooks.isEmpty()) {
            return Optional.ofNullable(retrievedBooks.get(0).toDomain());
        } else {
            return Optional.empty();
        }
    }

    public Optional<Book> findBorrowedBookForUser(String userId, String bookName){
       return bookRepository.findByBorrowerAndName(userId, bookName).map(MongoBook::toDomain);
    }

}
