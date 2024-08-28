package com.example.tasksmanager.exceptions;

import java.io.IOException;

public class ErrorRegisterUserException extends IOException {
    public ErrorRegisterUserException(String msg) {
        super(msg);
    }
}
