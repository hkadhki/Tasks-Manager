package com.example.tasksmanager.exceptions;

import java.io.IOException;

public class ErrorEditTaskException extends IOException {
    public ErrorEditTaskException(String msg) {
        super(msg);
    }
}
