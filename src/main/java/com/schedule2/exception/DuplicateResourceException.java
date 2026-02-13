package com.schedule2.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends ServiceException {
    public DuplicateResourceException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
