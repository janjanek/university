package com.university.bibliotheca.service;

import com.university.bibliotheca.adapter.UserDto;
import com.university.bibliotheca.adapter.mongo.MongoUserAdapter;
import com.university.bibliotheca.domain.model.User;
import com.university.bibliotheca.service.exception.BookAlreadyBorrowedException;
import com.university.bibliotheca.service.exception.BookAlreadyReservedException;
import com.university.bibliotheca.service.exception.BookNotBorrowedException;
import com.university.bibliotheca.service.exception.BookNotReservedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private MongoUserAdapter mongoUserAdapter;

    @Autowired
    public UserService(MongoUserAdapter mongoUserAdapter) {
        this.mongoUserAdapter = mongoUserAdapter;
    }

    public void saveUser(User user) {
        mongoUserAdapter.saveUser(user);
    }

    public User findUser(String id) {
        return mongoUserAdapter.findUser(id);
    }


    public void addReservationToUser(User user, String bookName) {
        List<String> reservedBookNames = user.getReservedBookNames();
        if (reservedBookNames != null) {
            if (!reservedBookNames.contains(bookName)) {
                reservedBookNames.add(bookName);
                User updatedUser = new User(user.getId(), user.getName(), user.getOccupation(), user.getBorrowedBookIds(), reservedBookNames);
                mongoUserAdapter.saveUser(updatedUser);
            } else {
                throw (new BookAlreadyReservedException(bookName));
            }
        } else {
            User updatedUser = new User(user.getId(), user.getName(), user.getOccupation(), user.getBorrowedBookIds(), List.of(bookName));
            mongoUserAdapter.saveUser(updatedUser);
        }
    }

    public void addBorrowToUser(String userId, String bookId) {
        User user = findUser(userId);
        List<String> borrowedBookIds = user.getBorrowedBookIds();
        if (borrowedBookIds != null) {
            if (!borrowedBookIds.contains(bookId)) {//TODO Investigate this exception.
                borrowedBookIds.add(bookId);
                User updatedUser = new User(user.getId(), user.getName(), user.getOccupation(), borrowedBookIds, user.getReservedBookNames());
                mongoUserAdapter.saveUser(updatedUser);
            } else {
                throw (new BookAlreadyBorrowedException(bookId));
            }
        } else {
            User updatedUser = new User(user.getId(), user.getName(), user.getOccupation(), List.of(bookId), user.getReservedBookNames());
            mongoUserAdapter.saveUser(updatedUser);
        }
    }

    public void removeReservationFromUser(User user, String bookName) {
        List<String> reservedBookNames = user.getReservedBookNames();
        if (reservedBookNames != null) {
            if (reservedBookNames.contains(bookName)) {
                reservedBookNames.remove(bookName);
                User updatedUser = new User(user.getId(), user.getName(), user.getOccupation(), user.getBorrowedBookIds(), reservedBookNames);
                mongoUserAdapter.saveUser(updatedUser);
            } else {
                throw (new BookNotReservedException(bookName));
            }
        } else {
            throw (new BookNotReservedException(bookName));
        }
    }

    public void removeBorrowFromUser(String userId, String bookId) {
        User user = findUser(userId);
        List<String> borrowedBookIds = user.getBorrowedBookIds();
        if (borrowedBookIds != null) {
            if (borrowedBookIds.contains(bookId)) {
                borrowedBookIds.remove(bookId);
                User updatedUser = new User(user.getId(), user.getName(), user.getOccupation(), borrowedBookIds, user.getReservedBookNames());
                mongoUserAdapter.saveUser(updatedUser);
            } else {
                throw (new BookNotBorrowedException(bookId));
            }
        } else {
            throw (new BookNotBorrowedException(bookId));
        }
    }

    public UserDto findUserDto(String id) {
        return mongoUserAdapter.findUserDto(id);
    }
}
