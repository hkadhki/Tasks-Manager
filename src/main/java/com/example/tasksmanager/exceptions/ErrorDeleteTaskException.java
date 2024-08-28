package com.example.tasksmanager.exceptions;

import java.io.IOException;

public class ErrorDeleteTaskException extends IOException {
    public ErrorDeleteTaskException(String msg) {
        super(msg);
    }
}
