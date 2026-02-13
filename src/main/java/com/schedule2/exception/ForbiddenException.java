package com.schedule2.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ServiceException{
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN,message);
    }
}
