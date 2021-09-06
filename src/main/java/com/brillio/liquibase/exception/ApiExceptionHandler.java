package com.brillio.liquibase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<?> userAlreadyExists(UserExistsException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNullPointerException.class)
    public ResponseEntity<?> userNullPointerException(UserNullPointerException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_IMPLEMENTED);
    }
}