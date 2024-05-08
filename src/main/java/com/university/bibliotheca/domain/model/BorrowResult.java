package com.university.bibliotheca.domain.model;

import org.springframework.http.HttpStatus;

public enum BorrowResult {
    BORROWED(HttpStatus.OK),
    ALREADY_RESERVED(HttpStatus.CONFLICT),
    RESERVED(HttpStatus.CREATED);

    private HttpStatus status;

    BorrowResult(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
