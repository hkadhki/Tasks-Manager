package com.example.tasksmanager.exceptions;

public class UnauthorizedErrorException extends IllegalArgumentException {
    public UnauthorizedErrorException(String msg) {
        super(msg);
    }
}
