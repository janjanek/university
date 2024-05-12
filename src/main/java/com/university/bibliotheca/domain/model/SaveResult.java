package com.university.bibliotheca.domain.model;

import org.springframework.http.HttpStatus;

public enum SaveResult {
    SAVED(HttpStatus.OK),
    SAVED_AND_BORROWED(HttpStatus.CREATED);

    private HttpStatus status;

    SaveResult(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
