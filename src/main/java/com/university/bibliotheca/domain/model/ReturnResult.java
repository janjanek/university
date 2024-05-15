package com.university.bibliotheca.domain.model;

import org.springframework.http.HttpStatus;

public enum ReturnResult {
    RETURNED(HttpStatus.OK),
    RETURNED_AND_BORROWED(HttpStatus.CREATED),
    NOT_OWNED(HttpStatus.CONFLICT);

    private HttpStatus status;

    ReturnResult(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
