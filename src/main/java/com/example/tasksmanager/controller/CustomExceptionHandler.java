package com.example.tasksmanager.controller;


import com.example.tasksmanager.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(ErrorCreateTaskException.class)
    public ResponseStatusException errorCreateTaskException(ErrorCreateTaskException e) {
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(ErrorDeleteTaskException.class)
    public ResponseStatusException errorDeleteTaskException(ErrorDeleteTaskException e) {
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(ErrorEditTaskException.class)
    public ResponseStatusException errorEditTaskException(ErrorEditTaskException e) {
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(ErrorInputDataException.class)
    public ResponseStatusException errorInputDataException(ErrorInputDataException e) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ErrorRegisterUserException.class)
    public ResponseStatusException errorRegisterUserException(ErrorRegisterUserException e) {
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseStatusException unauthorizedErrorException(UnauthorizedErrorException e) {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseStatusException httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ErrorPermissionException.class)
    public ResponseStatusException errorPermissionException(ErrorPermissionException e) {
        return new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
    }


}
