package com.example.tasksmanager.exceptions;

public class ErrorInputDataException extends IllegalArgumentException {
    public ErrorInputDataException(String msg) {
        super(msg);
    }
}
