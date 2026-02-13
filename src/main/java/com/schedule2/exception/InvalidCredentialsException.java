package com.schedule2.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ServiceException{
    public InvalidCredentialsException(String message) {
        super(HttpStatus.UNAUTHORIZED,message);
    }
}
