package com.university.bibliotheca.domain.model;

import org.springframework.http.HttpStatus;

public enum BorrowResult {
    BORROWED(HttpStatus.OK),
    RESERVED(HttpStatus.CREATED),
    ALREADY_RESERVED(HttpStatus.CONFLICT),
    ALREADY_BORROWED(HttpStatus.CONFLICT);

    private HttpStatus status;

    BorrowResult(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
