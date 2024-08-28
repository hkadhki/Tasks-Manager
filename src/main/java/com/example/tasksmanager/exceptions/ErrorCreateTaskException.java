package com.example.tasksmanager.exceptions;

import java.io.IOException;

public class ErrorCreateTaskException extends IOException {
    public ErrorCreateTaskException(String msg) {
        super(msg);
    }
}
