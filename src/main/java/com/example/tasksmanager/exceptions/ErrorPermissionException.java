package com.example.tasksmanager.exceptions;

public class ErrorPermissionException extends IllegalArgumentException {
    public ErrorPermissionException(String msg) {
        super(msg);
    }
}
